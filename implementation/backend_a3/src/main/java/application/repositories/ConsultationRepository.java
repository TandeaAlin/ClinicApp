package application.repositories;

import application.model.Consultation;
import application.model.Doctor;
import application.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {
    List<Consultation> findByStartTimeBetween(Date start, Date end);

    List<Consultation> findByDoctor(Doctor doctor);

    List<Consultation> findByDoctorAndPatient(Doctor doctor, Patient patient);

    List<Consultation> findByDoctorAndStartTimeAfter(Doctor doctor, Date start);
}
