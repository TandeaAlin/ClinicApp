package application.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="WorkingHour")
public class WorkingHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="startHour")
    int startHour;

    @Column(name="endHour")
    int endHour;

    @Column(name="dayOfWeek")
    int dayOfWeek;

    @ManyToOne
    @JoinColumn(name="doctorId")
    @JsonBackReference
    Doctor doctor;

    @JsonIgnore
    public int getId() {
        return id;
    }

    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "WorkingHour{" +
                "id=" + id +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", dayOfWeek=" + dayOfWeek +
                ", doctor=" + doctor +
                '}';
    }
}
