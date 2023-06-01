package finki.ukim.mk.virusfilescannerservice.service.impl;

import fi.solita.clamav.ClamAVClient;
import finki.ukim.mk.virusfilescannerservice.model.WebhookPayload;
import finki.ukim.mk.virusfilescannerservice.service.FileVirusScanService;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FileVirusScanServiceImpl implements FileVirusScanService {

    @Value("${minio.bucketName}")
    private String minioBucket;

    private final ClamAVClient clamAVClient;
    private final MinioClient minioClient;
    private final KafkaTemplate<String, Boolean> kafkaTemplate;

    public FileVirusScanServiceImpl(ClamAVClient clamAVClient, MinioClient minioClient, KafkaTemplate<String, Boolean> kafkaTemplate) {
        this.clamAVClient = clamAVClient;
        this.minioClient = minioClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "file-scan-topic", groupId = "virus-file-scanner-group")
    public void scanFileForViruses(WebhookPayload payload) {
        Boolean isInfected = false;
        try {
            log.info("Kafka message was recieved here.");
            // Scan the file using ClamAV

            // in Object.key we have the name of the object
            String objectName = payload.getRecords().get(0).getS3().getObject().getKey();
            byte[] fileInputStream = minioClient.getObject(minioBucket, objectName).readAllBytes();

            isInfected = this.clamAVClient.isCleanReply(fileInputStream);

            if (isInfected) {
                // Infected file detected
                System.out.println("The file is infected, and will be blocked.");
            } else {
                // File is clean
                System.out.println("The file is clean.");
            }
            sendIfFileInfected(isInfected);
            log.info("File scanned with ClamAV client.");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            log.info("File not scanned succesfully.");
        }
    }

    public void sendIfFileInfected(Boolean isInfected){
        try {
            kafkaTemplate.send("corrupted-file-topic", isInfected);
            log.info("Kafka sent a message.");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception thrown in method sendIfFileInfected.");
        }
    }

}
