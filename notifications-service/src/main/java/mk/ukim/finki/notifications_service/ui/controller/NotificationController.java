package mk.ukim.finki.notifications_service.ui.controller;

import lombok.extern.log4j.Log4j2;
import mk.ukim.finki.notifications_service.service.MailService;
import mk.ukim.finki.notifications_service.ui.dao.SendMailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class NotificationController {

    private final MailService mailService;

    public NotificationController(MailService mailService) {
        this.mailService = mailService;
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

}
