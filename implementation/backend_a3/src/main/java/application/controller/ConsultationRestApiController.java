package application.controller;

import application.exceptions.InvalidDataException;
import application.model.Consultation;
import application.model.Doctor;
import application.model.Patient;
import application.service.ConsultationService;
import application.service.DoctorService;
import application.service.PatientService;
import application.validators.PatientValidator;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConsultationRestApiController {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PreAuthorize("hasRole('ROLE_SECRETARY') || hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = "/consultation/", method = RequestMethod.GET)
    public ResponseEntity<?> listAllConsultations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isDoctor = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOCTOR"));

        List<Consultation> consultations;

        if(isDoctor){
            Doctor doctor = this.doctorService.findByUsername(auth.getName());
            consultations = this.consultationService.findByDoctor(doctor);
        } else {
            consultations = this.consultationService.findAll();
        }

        if (consultations.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = "/consultation/upcoming", method = RequestMethod.GET)
    public ResponseEntity<?> listUpcomingConsultations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Doctor doctor = this.doctorService.findByUsername(auth.getName());

        List<Consultation> consultations = this.consultationService.findUpcomingConsultations(doctor);

        if (consultations.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = "/consultation/patientId={id}", method = RequestMethod.GET)
    public ResponseEntity<?> getConsultationsByPatientId(@PathVariable("id") int patientId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Doctor doctor = this.doctorService.findByUsername(auth.getName());

        Patient patient = this.patientService.findById(patientId);

        if(patient == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        List<Consultation> consultations = this.consultationService.findByDoctorAndPatient(doctor,patient);

        if (consultations.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY') || hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = "/consultation/id={id}", method = RequestMethod.GET)
    public ResponseEntity<?> getConsultation(@PathVariable("id") int id) {
        Consultation consultation = this.consultationService.findById(id);

        if (consultation == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(consultation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/consultation/day={day}&doctorId={id}", method = RequestMethod.GET)
    public ResponseEntity<?> getConsultationByDay(@PathVariable("day") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) String day, @PathVariable("id") int doctorId) {
        ISO8601DateFormat format = new ISO8601DateFormat();

        Date parsedDay;
        try {
            parsedDay = format.parse(day);
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Doctor doctor = this.doctorService.findById(doctorId);
        List<Consultation> consultations = this.consultationService.findByDay(doctor, parsedDay);

        if( consultations.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/consultation/", method = RequestMethod.POST)
    public ResponseEntity<?> createConsultation(@RequestBody Consultation consultation) {
        Consultation newConsultation;

        try {
            newConsultation = this.consultationService.saveConsultation(consultation);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newConsultation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY') || hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = "/consultation/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateConsultation(@PathVariable("id") int id, @RequestBody Consultation consultation) {
        Consultation currentConsultation = this.consultationService.findById(id);

        if (currentConsultation == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        try {
            currentConsultation = this.consultationService.updateConsultation(consultation);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(currentConsultation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/consultation/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteConsultation(@PathVariable("id") int id) {
        Consultation consultation = this.consultationService.findById(id);

        if (consultation == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        this.consultationService.deleteConsultationById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
