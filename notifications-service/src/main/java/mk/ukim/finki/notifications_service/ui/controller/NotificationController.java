package mk.ukim.finki.notifications_service.ui.controller;

import lombok.extern.log4j.Log4j2;
import mk.ukim.finki.notifications_service.messaging.NotificationProducer;
import mk.ukim.finki.notifications_service.model.PostNotification;
import mk.ukim.finki.notifications_service.service.MailService;
import mk.ukim.finki.notifications_service.ui.dao.SendMailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class NotificationController {

    private final MailService mailService;
    private final NotificationProducer notificationProducer;

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

    @GetMapping(path = "/testMessaging")
    public String testMessaging() {

        PostNotification message = PostNotification.builder()
                .recipientEmail("somemail@gmail.com")
                .notificationContent("Some random content.")
                .build();
        notificationProducer.sendNotificationMessage("posts", message);

        return "Tested.";
    }

}
