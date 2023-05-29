package finki.ukim.mk.virusfilescannerservice;

import finki.ukim.mk.virusfilescannerservice.model.MinioUploadedFile;
import io.minio.MinioClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MinioFileUploadEventListener {

    @Value("${minio.bucketName}")
    private String minioBucket;

    private final MinioClient minioClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MinioFileUploadEventListener(MinioClient minioClient, KafkaTemplate<String, String> kafkaTemplate) {
        this.minioClient = minioClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    public void handleNewFileEvent(String objectName) {
        MinioUploadedFile minioUploadedFile = new MinioUploadedFile();
        try {
            // Publish the file to Kafka
            byte[] fileInputStream = minioClient.getObject(minioBucket, objectName).readAllBytes();
            minioUploadedFile.setFileName(objectName);
            minioUploadedFile.setFileInputStream(fileInputStream);
            kafkaTemplate.send("file-scan-topic", objectName);
            log.info("Kafka sent message.");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            minioUploadedFile.setErrorMessage(e.getMessage());
            kafkaTemplate.send("file-scan-topic", objectName);
            log.info("Kafka sent message with error.");
        }
    }
}
