package mk.ukim.finki.notifications_service.ui.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record SendMailRequest (
        String to,
        String subject,
        String text) {
}
