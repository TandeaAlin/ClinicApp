package application.model.builders;

import application.model.Consultation;
import application.model.Doctor;
import application.model.Observation;
import application.model.Patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultationBuilder {
    private Patient patient;
    private Doctor doctor;
    private List<Observation> observations = new ArrayList<>();
    private Date startTime;
    private Date endTime;

    public ConsultationBuilder setPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public ConsultationBuilder setDoctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public ConsultationBuilder setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public ConsultationBuilder setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Consultation createConsultation() {
        return new Consultation(patient, doctor, observations, startTime, endTime);
    }
}