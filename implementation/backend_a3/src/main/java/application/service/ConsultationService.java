package application.service;

import application.model.Consultation;

import java.util.Date;
import java.util.List;

public interface ConsultationService {

    Consultation findById(int id);

    List<Consultation> findAll();

    List<Consultation> findByDay(Date day);

    Consultation saveConsultation(Consultation consultation);

    Consultation updateConsultation(Consultation consultation);

    void deleteConsultationById(int id);
}
