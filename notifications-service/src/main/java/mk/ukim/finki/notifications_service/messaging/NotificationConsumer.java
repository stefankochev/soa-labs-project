package mk.ukim.finki.notifications_service.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import mk.ukim.finki.notifications_service.model.PostNotification;
import mk.ukim.finki.notifications_service.model.enumeration.MailStatus;
import mk.ukim.finki.notifications_service.service.MailService;
import mk.ukim.finki.notifications_service.service.PostNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class NotificationConsumer {

    private final ObjectMapper objectMapper;
    private final MailService mailService;

    @Autowired
    private PostNotificationService postNotificationService;

    public NotificationConsumer(ObjectMapper objectMapper, MailService mailService) {
        this.objectMapper = objectMapper;
        this.mailService = mailService;
    }

    @KafkaListener(topics = "posts")
    public void receiveNotificationMessage(String message) {

        try {
            log.info("Received following message: " + message);
            PostNotification postNotification = objectMapper.readValue(message, PostNotification.class);
            if (postNotification.getSendDate() != null) {
                postNotification.setMailStatus(MailStatus.WAITING);
                System.out.println("Kafka should save");
                postNotificationService.createPostNotification(postNotification);
                System.out.println("kafka saved");
            } else {
                mailService.sendMail(postNotification.getRecipientEmail(),
                        postNotification.getSubject(),
                        postNotification.getNotificationContent());
            }

        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }

}
