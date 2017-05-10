package application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="doctorId")
    private Doctor doctor;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "consultation", orphanRemoval = true)
    @JsonManagedReference
    private List<Observation> observations;

    @Column(name="startsAt", nullable=false)
    private Date startTime;

    @Column(name="endsAt", nullable=false)
    private Date endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", patient=" + patient.getFullName() +
                ", doctor=" + doctor.getFullName() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
