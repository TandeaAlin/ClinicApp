package application.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="seen")
    private boolean seen;

    @Column(name="text")
    private String text;

    @Column(name="createdAt")
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "reference")
    private Consultation consultation;

    public Notification(boolean seen, String text, Date createdAt, Consultation consultation) {
        this.seen = seen;
        this.text = text;
        this.createdAt = createdAt;
        this.consultation = consultation;
    }

    public Notification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
