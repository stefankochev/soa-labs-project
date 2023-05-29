package finki.ukim.mk.virusfilescannerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VirusScanResponse {
    private String fileName;
    private Boolean detectedVirus;
    private String errorMessage = "";
}