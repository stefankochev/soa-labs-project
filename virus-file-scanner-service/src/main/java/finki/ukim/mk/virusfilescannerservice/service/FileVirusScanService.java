package finki.ukim.mk.virusfilescannerservice.service;

import finki.ukim.mk.virusfilescannerservice.model.MinioUploadedFile;
import finki.ukim.mk.virusfilescannerservice.model.WebhookPayload;

public interface FileVirusScanService {
    public void scanFileForViruses(WebhookPayload payload);
}
