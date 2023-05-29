package finki.ukim.mk.virusfilescannerservice.rest;

import finki.ukim.mk.virusfilescannerservice.MinioFileUploadEventListener;
import finki.ukim.mk.virusfilescannerservice.model.WebhookPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebHookMinioController {

    private final MinioFileUploadEventListener fileUploadEventListener;

    @Autowired
    public WebHookMinioController(MinioFileUploadEventListener fileUploadEventListener) {
        this.fileUploadEventListener = fileUploadEventListener;
    }

    @PostMapping("/webhook")  // Endpoint URL configured in Minio event notifications
    public ResponseEntity<String> handleWebhookEvent(@RequestBody WebhookPayload payload) {
        // here we have the name of the object uploaded to the bucket
        String objectName = payload.getRecords().get(0).getS3().getObject().getKey();

        // Invoke the file upload event listener
        fileUploadEventListener.handleNewFileEvent(objectName);

        return ResponseEntity.ok("Webhook received successfully.");
    }
}