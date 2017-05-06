package application.service;


import application.model.Consultation;
import application.repositories.ConsultationRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService{

    @Autowired
    private ConsultationRepository consultationRepository;

    public Consultation findById(int id) {
        return this.consultationRepository.findOne(id);
    }

    public List<Consultation> findAll() {
        return this.consultationRepository.findAll();
    }

    public List<Consultation> findByDay(Date day) {
        Date startDate = day;
        Date endDate = DateUtils.addDays(startDate,1);

        return this.consultationRepository.findByStartTimeBetween(startDate,endDate);
    }

    public Consultation saveConsultation(Consultation consultation) {
        return this.consultationRepository.save(consultation);
    }

    public Consultation updateConsultation(Consultation consultation) {
        return this.consultationRepository.save(consultation);
    }

    public void deleteConsultationById(int id) {
        this.consultationRepository.delete(id);
    }
}
