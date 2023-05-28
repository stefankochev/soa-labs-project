package mk.ukim.finki.notifications_service.service.impl;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import mk.ukim.finki.notifications_service.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
                         String text) throws MessagingException {

        log.info("Mail would be sent now to: "+to+"\nSubject: "+subject+"\nWith text: "+text);

//        TODO: Implement mail sending
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setFrom("soanotificaiton@gmail.com");
        helper.setSubject(subject);
        helper.setText(text);
        javaMailSender.send(mimeMessage);
    }

}
