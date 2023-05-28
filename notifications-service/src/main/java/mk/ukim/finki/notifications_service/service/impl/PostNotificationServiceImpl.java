package mk.ukim.finki.notifications_service.service.impl;

import jakarta.mail.MessagingException;
import mk.ukim.finki.notifications_service.model.PostNotification;
import mk.ukim.finki.notifications_service.model.enumeration.MailStatus;
import mk.ukim.finki.notifications_service.repository.PostNotificationRepository;
import mk.ukim.finki.notifications_service.service.MailService;
import mk.ukim.finki.notifications_service.service.PostNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostNotificationServiceImpl implements PostNotificationService {

    @Autowired
    private MailService mailService;

    private final PostNotificationRepository postNotificationRepository;

    public PostNotificationServiceImpl(PostNotificationRepository postNotificationRepository) {
        this.postNotificationRepository = postNotificationRepository;
    }

    public PostNotification createPostNotification(PostNotification postNotification) {
        System.out.println("Should save in db");
        postNotificationRepository.save(postNotification);
        System.out.println("saved in db");
        return postNotification;
    }

    public Optional<PostNotification> getPostNotificationById(Long id) {
        return postNotificationRepository.findById(id);
    }

    public List<PostNotification> getAllPostNotifications() {
        return postNotificationRepository.findAll();
    }

    public void deletePostNotification(Long id) {
        postNotificationRepository.deleteById(id);
    }

    public PostNotification updatePostNotification(Long id, PostNotification updatedPostNotification) {
        PostNotification existingPostNotification = postNotificationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("PostNotification not found with id: " + id));

        existingPostNotification.setRecipientEmail(updatedPostNotification.getRecipientEmail());
        existingPostNotification.setNotificationContent(updatedPostNotification.getNotificationContent());
        existingPostNotification.setMailStatus(updatedPostNotification.getMailStatus());

        return postNotificationRepository.save(existingPostNotification);
    }

    @Override
    public void sendSchedulerNotifications() throws MessagingException {
        List<PostNotification> notifications = this.findAllByStatus(MailStatus.WAITING);
        notifications = notifications.stream()
                .filter(x -> compareDates(x.getSendDate(), LocalDateTime.now()) <= 0)
                .collect(Collectors.toList());

        for (PostNotification notification : notifications) {
            mailService.sendMail(notification.getRecipientEmail(), notification.getSubject(), notification.getNotificationContent());
            notification.setMailStatus(MailStatus.COMPLETED);
            this.updatePostNotification(notification.getId(), notification);
        }
    }

    public List<PostNotification> getPostNotificationsByRecipientEmail(String recipientEmail) {
        return postNotificationRepository.findByRecipientEmail(recipientEmail);
    }

    public List<PostNotification> getPostNotificationsByNotificationContentContaining(String keyword) {
        return postNotificationRepository.findByNotificationContentContaining(keyword);
    }

    public long countPostNotifications() {
        return postNotificationRepository.count();
    }

    @Override
    public List<PostNotification> findAllByStatus(MailStatus status) {
        return postNotificationRepository.findByMailStatus(status);
    }

    private long compareDates(LocalDateTime date, LocalDateTime date2) {
        System.out.println("compared dates" + ChronoUnit.MINUTES.between(date, date2));
        return ChronoUnit.SECONDS.between(date2, date);
    }

}
