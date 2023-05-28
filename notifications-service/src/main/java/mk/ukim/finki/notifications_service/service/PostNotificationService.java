package mk.ukim.finki.notifications_service.service;

import jakarta.mail.MessagingException;
import mk.ukim.finki.notifications_service.model.PostNotification;
import mk.ukim.finki.notifications_service.model.enumeration.MailStatus;

import java.util.List;
import java.util.Optional;

public interface PostNotificationService {

    PostNotification createPostNotification(PostNotification postNotification);

    Optional<PostNotification> getPostNotificationById(Long id);

    List<PostNotification> getAllPostNotifications();

    void deletePostNotification(Long id);

    PostNotification updatePostNotification(Long id, PostNotification updatedPostNotification);

    void sendSchedulerNotifications() throws MessagingException;

    // Additional methods

    List<PostNotification> getPostNotificationsByRecipientEmail(String recipientEmail);

    List<PostNotification> getPostNotificationsByNotificationContentContaining(String keyword);

    long countPostNotifications();

    List<PostNotification> findAllByStatus(MailStatus status);
}
