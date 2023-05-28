package mk.ukim.finki.notifications_service.service;

import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;

public interface MailService {

    void sendMail(String to, String subject, String text) throws MessagingException;

}
