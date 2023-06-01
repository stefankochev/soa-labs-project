package finki.ukim.mk.virusfilescannerservice;

import finki.ukim.mk.virusfilescannerservice.model.WebhookPayload;
import io.minio.MinioClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Log4j2
public class MinioFileUploadEventListener {

    private final KafkaTemplate<String, WebhookPayload> kafkaTemplate;

    @Autowired
    public MinioFileUploadEventListener(MinioClient minioClient, KafkaTemplate<String, WebhookPayload> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "test-topic", groupId = "virus-file-scanner-group")
    public void handleNewUploadedFileEvent(WebhookPayload payload) {
        try {
            sendInputStreamForScan(payload);
            log.info("Object retrived OK from MinIO.");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            log.info("Object not retrived OK from MinIO.");
        }
    }

    public void sendInputStreamForScan(WebhookPayload payload){
        try {
            kafkaTemplate.send("file-scan-topic", payload);
            log.info("Kafka sent message.");
        } catch (Exception e) {
            log.info("Exception thrown in method sentInputStreamForScan.");
        }
    }
}
