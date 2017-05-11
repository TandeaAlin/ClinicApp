package application.service;


import application.exceptions.InvalidDataException;
import application.model.Consultation;
import application.model.Doctor;
import application.model.Patient;
import application.model.WorkingHour;
import application.repositories.ConsultationRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.DateUtil;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConsultationServiceImpl implements ConsultationService{

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private DoctorService doctorService;


    public Consultation findById(int id) {
        return this.consultationRepository.findOne(id);
    }

    public List<Consultation> findAll() {
        return this.consultationRepository.findAll();
    }

    public List<Consultation> findByDay(Doctor doctor, Date day) {
        Date startDate = day;
        Date endDate = DateUtils.addDays(startDate,1);

        return this.consultationRepository.findByDoctorAndStartTimeBetween(doctor, startDate,endDate);
    }

    public List<Consultation> findByDay(Patient patient, Date day) {
        Date startDate = day;
        Date endDate = DateUtils.addDays(startDate,1);

        return this.consultationRepository.findByPatientAndStartTimeBetween(patient, startDate,endDate);
    }

    public List<Consultation> findByDoctor(Doctor doctor) {
        return this.consultationRepository.findByDoctor(doctor);
    }

    public List<Consultation> findUpcomingConsultations(Doctor doctor) {
        Date now = new Date(System.currentTimeMillis());
        return this.consultationRepository.findByDoctorAndStartTimeAfter(doctor, now);
    }

    public List<Consultation> findByDoctorAndPatient(Doctor doctor, Patient patient) {
        return this.consultationRepository.findByDoctorAndPatient(doctor, patient);
    }

    public Consultation saveConsultation(Consultation consultation) throws InvalidDataException {
        if(consultation.getStartTime().after(consultation.getEndTime())){
            throw new InvalidDataException("This consultation ends before it begins.");
        }

        Date now = new Date(System.currentTimeMillis());
        now = DateUtils.setSeconds(now, 0);
        now = DateUtils.setMinutes(now, 0);
        if(consultation.getStartTime().before(now)){
            throw new InvalidDataException("The consultation cannot be scheduled in the past");
        }

        validateConsultation(consultation); // An exception will be thrown if any error is found

        return this.consultationRepository.save(consultation);
    }

    public Consultation updateConsultation(Consultation consultation) throws InvalidDataException {
        Consultation c1 = this.consultationRepository.findOne(consultation.getId());

        if(c1.getStartTime().equals(consultation.getStartTime()) && c1.getEndTime().equals(consultation.getEndTime())){
            return this.saveConsultation(consultation); // Time changed => Treat it as a new consultation
        } else {
            validateConsultation(consultation); // If the time we have less things to validate

            return this.consultationRepository.save(consultation);
        }
    }

    public void deleteConsultationById(int id) {
        this.consultationRepository.delete(id);
    }

    private void validateConsultation(Consultation consultation) throws InvalidDataException {
        if(!this.validateDoctorSchedule(consultation)){
            throw new InvalidDataException("The doctor isn't available at that time");
        }

        if(!this.validateDoctorOverlaps(consultation)){
            throw new InvalidDataException("The doctor has another consultation at that time");
        }

        if(!this.validatePatientOverlaps(consultation)){
            throw new InvalidDataException("The patient is scheduled for another consultation at that time");
        }
    }

    // Returns true if the consultation is inside the doctor's working hours
    private boolean validateDoctorSchedule(Consultation consultation){
        LocalDateTime start = consultation.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Set<WorkingHour> schedule = consultation.getDoctor().getWorkingHours();
        schedule = schedule.stream()
                .filter(workingHour -> workingHour.getDayOfWeek()==start.getDayOfWeek().getValue())
                .collect(Collectors.toSet());

        if(schedule.isEmpty()){
            return false;
        }

        for(WorkingHour workingHour: schedule){
            if(start.getHour() >= workingHour.getStartHour()){
                LocalDateTime end = consultation.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                if(end.getHour() < workingHour.getEndHour() || (end.getHour() == workingHour.getEndHour() && end.getMinute()== 0 )){
                    return true;
                }
            }
        }

        return false;
    }

    // Returns true if the consultation does not overlap with other consultations held by the same doctor
    private boolean validateDoctorOverlaps(Consultation consultation){
        Date day = getBeginningOfDay(consultation);

        List<Consultation> consultations = this.findByDay(consultation.getDoctor(), day);

        if (checkOverlaps(consultation, consultations))
            return false;

        return true;
    }

    private boolean validatePatientOverlaps(Consultation consultation){
        Date day = getBeginningOfDay(consultation);

        List<Consultation> consultations = this.findByDay(consultation.getPatient(), day);

        if (checkOverlaps(consultation, consultations))
            return false;

        return true;
    }

    private boolean checkOverlaps(Consultation consultation, List<Consultation> consultations) {
        if(!consultations.isEmpty()){
            long start = consultation.getStartTime().getTime();
            long end = consultation.getEndTime().getTime();

            for(Consultation other: consultations){
                if(other.getId() != consultation.getId()){ // A consultation will not overlap with itself
                    if(start >= other.getStartTime().getTime() && start< other.getEndTime().getTime()){
                        return true; // Overlap detected. Start moment is contained in the older consultation
                    }

                    if(end > other.getStartTime().getTime() && end <= other.getEndTime().getTime()) {
                        return true; // Overlap detected. End moment is contained in the older consultation
                    }

                    if(other.getStartTime().getTime() >= start && other.getStartTime().getTime() < end){
                        return true; // Overlap detected. Older consultation is contained in the new one
                    }
                }
            }
        }
        return false;
    }

    private Date getBeginningOfDay(Consultation consultation) {
        Date day = consultation.getStartTime();
        day = DateUtils.setHours(day, 0);
        day = DateUtils.setMinutes(day, 0);
        day = DateUtils.setSeconds(day, 0);
        day = DateUtils.setMilliseconds(day, 0);
        return day;
    }
}
