package finki.ukim.mk.virusfilescannerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class VirusFileScannerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirusFileScannerServiceApplication.class, args);
    }

}
