package finki.ukim.mk.virusfilescannerservice.service.impl;

import fi.solita.clamav.ClamAVClient;
import finki.ukim.mk.virusfilescannerservice.model.MinioUploadedFile;
import finki.ukim.mk.virusfilescannerservice.model.VirusScanResponse;
import finki.ukim.mk.virusfilescannerservice.service.FileVirusScanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FileVirusScanServiceImpl implements FileVirusScanService {

    private final ClamAVClient clamAVClient;
    private final KafkaTemplate<String, VirusScanResponse> kafkaTemplate;

    public FileVirusScanServiceImpl(ClamAVClient clamAVClient, KafkaTemplate<String, VirusScanResponse> kafkaTemplate) {
        this.clamAVClient = clamAVClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "file-scan-topic")
    public void scanFileForViruses(MinioUploadedFile file) {
        VirusScanResponse fileScanResponse = new VirusScanResponse();
        fileScanResponse.setFileName(file.getFileName());
        try {
            log.info("Kafka message was recieved here.");
            // Scan the file using ClamAV
            byte[] fileInputStream = file.getFileInputStream();
            boolean isInfected = this.clamAVClient.isCleanReply(fileInputStream);

            if (isInfected) {
                // Infected file detected
                System.out.println("The file is infected, and will be blocked.");
                fileScanResponse.setDetectedVirus(true);
            } else {
                // File is clean
                System.out.println("The file is clean.");
                fileScanResponse.setDetectedVirus(false);
            }
            fileScanResponse.setErrorMessage(null);
            kafkaTemplate.send("corrupted-file-topic", fileScanResponse);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            fileScanResponse.setErrorMessage(e.getMessage());
            kafkaTemplate.send("corrupted-file-topic", fileScanResponse);
        }
    }

}
