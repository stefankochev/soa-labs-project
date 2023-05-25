package mk.ukim.finki.scheduler_service.scheduler;

import mk.ukim.finki.scheduler_service.web.feign.NotificationsFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Component
public class NotificationScheduler {

    @Autowired
    private NotificationsFeignClient notificationsFeignClient;

    @Scheduled(cron = "0 */1 * ? * *")
    public void sendSchedulerNotifications() {
        notificationsFeignClient.sendScheduledNotifications();
    }
}
