package application.model.builders;

import application.model.Consultation;
import application.model.Notification;

import java.util.Date;

public class NotificationBuilder {
    private boolean seen = false;
    private String text;
    private Date createdAt;
    private Consultation consultation;

    public NotificationBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public NotificationBuilder setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public NotificationBuilder setConsultation(Consultation consultation) {
        this.consultation = consultation;
        return this;
    }

    public Notification createNotification() {
        return new Notification(seen, text, createdAt, consultation);
    }
}