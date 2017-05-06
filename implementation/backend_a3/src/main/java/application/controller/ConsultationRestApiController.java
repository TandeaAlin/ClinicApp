package application.controller;

import application.model.Consultation;
import application.service.ConsultationService;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConsultationRestApiController {
    @Autowired
    private ConsultationService consultationService;

    @RequestMapping(value = "/consultation/", method = RequestMethod.GET)
    public ResponseEntity<?> listAllConsultations() {
        List<Consultation> consultations = this.consultationService.findAll();

        if (consultations.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }

    @RequestMapping(value = "/consultation/id={id}", method = RequestMethod.GET)
    public ResponseEntity<?> getConsultation(@PathVariable("id") int id) {
        Consultation consultation = this.consultationService.findById(id);

        if (consultation == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(consultation, HttpStatus.OK);
    }

    @RequestMapping(value = "/consultation/day={day}", method = RequestMethod.GET)
    public ResponseEntity<?> getConsultationByDay(@PathVariable("day") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) String day) {
        ISO8601DateFormat format = new ISO8601DateFormat();

        Date parsedDay;
        try {
            parsedDay = format.parse(day);
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Consultation> consultations = this.consultationService.findByDay(parsedDay);

        if( consultations.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }

    @RequestMapping(value = "/consultation/", method = RequestMethod.POST)
    public ResponseEntity<?> createConsultation(@RequestBody Consultation consultation) {
        Consultation newConsultation;

        newConsultation = this.consultationService.saveConsultation(consultation);

        return new ResponseEntity<>(newConsultation, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/consultation/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateConsultation(@PathVariable("id") int id, @RequestBody Consultation consultation) {
        Consultation currentConsultation = this.consultationService.findById(id);

        if (currentConsultation == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        currentConsultation = this.consultationService.updateConsultation(consultation);

        return new ResponseEntity<>(currentConsultation, HttpStatus.OK);
    }

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
