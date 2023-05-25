package mk.ukim.finki.scheduler_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SchedulerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerServiceApplication.class, args);
	}

}
