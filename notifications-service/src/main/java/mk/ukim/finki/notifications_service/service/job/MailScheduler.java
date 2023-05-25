//package mk.ukim.finki.notifications_service.service.job;
//
//import mk.ukim.finki.notifications_service.model.PostNotification;
//import mk.ukim.finki.notifications_service.model.enumeration.MailStatus;
//import mk.ukim.finki.notifications_service.service.MailService;
//import mk.ukim.finki.notifications_service.service.PostNotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@EnableScheduling
//@Component
//public class MailScheduler {
//
//    @Autowired
//    private MailService mailService;
//
//    @Autowired
//    private PostNotificationService notificationService;
//
//    @Scheduled(cron = "0 */1 * ? * *")
//    public void sendMailsByDate() {
//        List<PostNotification> notifications = notificationService.findAllByStatus(MailStatus.WAITING);
//        notifications = notifications.stream()
//                .filter(x -> compareDates(x.getSendDate(), LocalDateTime.now()) <= 0)
//                .collect(Collectors.toList());
//
//        for (PostNotification notification : notifications) {
//            mailService.sendMail(notification.getRecipientEmail(), notification.getSubject(), notification.getNotificationContent());
//            notification.setMailStatus(MailStatus.COMPLETED);
//            notificationService.updatePostNotification(notification.getId(), notification);
//        }
//
//    }
//
//    private long compareDates(LocalDateTime date, LocalDateTime date2) {
//        System.out.println("compared dates" + ChronoUnit.MINUTES.between(date, date2));
//        return ChronoUnit.MINUTES.between(date2, date);
//    }
//
//}
