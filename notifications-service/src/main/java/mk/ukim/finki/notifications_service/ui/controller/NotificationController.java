package mk.ukim.finki.notifications_service.ui.controller;

import lombok.extern.log4j.Log4j2;
import mk.ukim.finki.notifications_service.messaging.NotificationProducer;
import mk.ukim.finki.notifications_service.model.PostNotification;
import mk.ukim.finki.notifications_service.service.MailService;
import mk.ukim.finki.notifications_service.service.PostNotificationService;
import mk.ukim.finki.notifications_service.ui.dao.SendMailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Log4j2
public class NotificationController {

    private final MailService mailService;
    private final NotificationProducer notificationProducer;

    @Autowired
    private PostNotificationService notificationService;

    public NotificationController(MailService mailService, NotificationProducer notificationProducer) {
        this.mailService = mailService;
        this.notificationProducer = notificationProducer;
    }

    @PostMapping(path = "/api/v1/notifications/sendMail")
    public ResponseEntity<Object> sendMail(@RequestBody SendMailRequest request) {

        try {
            mailService.sendMail(request.to(), request.subject(), request.text());
            return ResponseEntity.ok("Mail request has been fulfilled:\n"+ request);
        } catch (Exception e) {
            log.error("Caught exception: " + e.getMessage());
            return ResponseEntity.internalServerError().body("There was an error in sending the following mail:\n"
            + request.toString());
        }

    }

    @GetMapping(path = "/api/v1/notifications/scheduled")
    public void snedScheduledNotifications() {
        notificationService.sendSchedulerNotifications();
    }

    @GetMapping(path = "/testMessaging")
    public String testMessaging() {

        PostNotification message = PostNotification.builder()
                .recipientEmail("somemail@gmail.com")
                .notificationContent("Some random content.")
                .sendDate(LocalDateTime.now().plusMinutes(2))
                .build();
        notificationProducer.sendNotificationMessage("posts", message);

        return "Tested.";
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<PostNotification>> getAll() {
        return ResponseEntity.ok(notificationService.getAllPostNotifications());
    }

}
