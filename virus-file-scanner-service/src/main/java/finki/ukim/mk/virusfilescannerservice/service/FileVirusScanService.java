package finki.ukim.mk.virusfilescannerservice.service;

import finki.ukim.mk.virusfilescannerservice.model.MinioUploadedFile;

public interface FileVirusScanService {
    public void scanFileForViruses(MinioUploadedFile file);
}
