package application.service;

import application.exceptions.InvalidDataException;
import application.model.Consultation;
import application.model.Doctor;
import application.model.Patient;

import java.util.Date;
import java.util.List;

public interface ConsultationService {

    Consultation findById(int id);

    List<Consultation> findAll();

    List<Consultation> findByDay(Doctor doctor,Date day);

    List<Consultation> findByDoctor(Doctor doctor);

    List<Consultation> findUpcomingConsultations(Doctor doctor);

    List<Consultation> findByDoctorAndPatient(Doctor doctor, Patient patient);

    Consultation saveConsultation(Consultation consultation) throws InvalidDataException;

    Consultation updateConsultation(Consultation consultation) throws InvalidDataException;

    void deleteConsultationById(int id);
}
