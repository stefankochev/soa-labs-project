package finki.ukim.mk.virusfilescannerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MinioUploadedFile {
    private String fileName;
    private byte[] fileInputStream;
    private String errorMessage = "";
}
