package mk.ukim.finki.notifications_service.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import mk.ukim.finki.notifications_service.service.MailService;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Bootstrap {

    private final MailService mailService;

    public Bootstrap(MailService mailService) {
        this.mailService = mailService;
    }

    @PostConstruct
    public void initialFunction() {
        //For testing purposes.



    }

}
