package mk.ukim.finki.notifications_service.service.impl;


import lombok.extern.log4j.Log4j2;
import mk.ukim.finki.notifications_service.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Function for sending a simple email.
     * @param to - Recipient email.
     * @param subject - Subject of email.
     * @param text - Content of email.
     */
    public void sendMail(String to,
                         String subject,
                         String text) {

        log.info("Mail would be sent now to: "+to+"\nSubject: "+subject+"\nWith text: "+text);

//        TODO: Implement mail sending
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        javaMailSender.send(message);
    }

}
