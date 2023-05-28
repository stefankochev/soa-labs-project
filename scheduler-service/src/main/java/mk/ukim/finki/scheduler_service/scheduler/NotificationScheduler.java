package mk.ukim.finki.scheduler_service.scheduler;

//import mk.ukim.finki.scheduler_service.web.feign.NotificationsFeignClient;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Component
@Log4j2
public class NotificationScheduler {

//    @Autowired
//    private NotificationsFeignClient notificationsFeignClient;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 */1 * ? * *")
    public void sendSchedulerNotifications() {
//        notificationsFeignClient.sendScheduledNotifications();
        String url = "http://notifications-service:8085/api/v1/notifications/scheduled";
        log.info("URL for notifications-service: "+url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        log.info("Response after calling"+response);
    }
}
