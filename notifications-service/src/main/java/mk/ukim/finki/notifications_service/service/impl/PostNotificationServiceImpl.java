package mk.ukim.finki.notifications_service.service.impl;

import mk.ukim.finki.notifications_service.model.PostNotification;
import mk.ukim.finki.notifications_service.repository.PostNotificationRepository;
import mk.ukim.finki.notifications_service.service.PostNotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostNotificationServiceImpl implements PostNotificationService {

    private final PostNotificationRepository postNotificationRepository;

    public PostNotificationServiceImpl(PostNotificationRepository postNotificationRepository) {
        this.postNotificationRepository = postNotificationRepository;
    }

    public PostNotification createPostNotification(PostNotification postNotification) {
        return postNotificationRepository.save(postNotification);
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

        return postNotificationRepository.save(existingPostNotification);
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

}
