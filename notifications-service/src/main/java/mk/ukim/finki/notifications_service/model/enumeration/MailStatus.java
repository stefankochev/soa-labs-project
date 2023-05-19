package mk.ukim.finki.notifications_service.model.enumeration;

public enum MailStatus {
    WAITING("Waiting"),
    COMPLETED("Completed"),
    FAILED("Failed");

    public final String value;

    MailStatus(String value) {
        this.value = value;
    }
}
