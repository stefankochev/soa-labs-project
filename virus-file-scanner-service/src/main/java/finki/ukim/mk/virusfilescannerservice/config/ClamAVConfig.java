package finki.ukim.mk.virusfilescannerservice.config;

import fi.solita.clamav.ClamAVClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClamAVConfig {

    @Bean
    public ClamAVClient clamAVClient() {
        return new ClamAVClient("clamav-server-host", 3310);
    }
}