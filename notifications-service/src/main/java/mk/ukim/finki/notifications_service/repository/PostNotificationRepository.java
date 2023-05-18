package mk.ukim.finki.notifications_service.repository;

import mk.ukim.finki.notifications_service.model.PostNotification;
import mk.ukim.finki.notifications_service.model.enumeration.MailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostNotificationRepository extends JpaRepository<PostNotification, Long> {

    List<PostNotification> findByRecipientEmail(String recipientEmail);

    List<PostNotification> findByNotificationContentContaining(String keyword);

    List<PostNotification> findByMailStatus(MailStatus status);

}
