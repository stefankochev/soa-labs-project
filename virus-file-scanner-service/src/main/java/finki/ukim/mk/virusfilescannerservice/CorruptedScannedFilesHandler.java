package finki.ukim.mk.virusfilescannerservice;

import finki.ukim.mk.virusfilescannerservice.model.VirusScanResponse;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CorruptedScannedFilesHandler {

    private final MinioClient minioClient;
    @Value("${minio.bucketName}")
    private String minioBucket;

    @Autowired
    public CorruptedScannedFilesHandler(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @KafkaListener(topics = "corrupted-file-topic")
    public void checkCorruptedFile(VirusScanResponse virusScanResponse) {
        try {
            boolean isCorrupted = virusScanResponse.getDetectedVirus();
            if (isCorrupted) {
                // Remove the corrupted file from Minio
                minioClient.removeObject(minioBucket, virusScanResponse.getFileName());
                // Update database entries as corrupted
//                updateDatabaseEntry(fileName);
                System.out.println("Corrupted file removed: " + virusScanResponse.getFileName());
            } else {
                // File is not corrupted, continue processing
                System.out.println("Non-corrupted file: " + virusScanResponse.getFileName());
                // Further processing logic goes here
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    private void updateDatabaseEntry(String fileName) {
        // Implement the logic to update the database entry for the corrupted file
    }
}
