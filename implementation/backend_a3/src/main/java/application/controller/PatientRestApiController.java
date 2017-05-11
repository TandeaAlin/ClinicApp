package application.controller;

import application.exceptions.DuplicateException;
import application.exceptions.InvalidDataException;
import application.model.Patient;
import application.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PatientRestApiController {

    @Autowired
    private PatientService patientService;

    @PreAuthorize("hasRole('ROLE_SECRETARY') || hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = "/patient/", method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        List<Patient> patients = this.patientService.findAllPatients();

        if (patients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/patient/id={id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable("id") int id) {
        Patient patient = this.patientService.findById(id);

        if (patient == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/patient/pnc={pnc}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientByPnc(@PathVariable("pnc") String pnc){
        Patient patient = this.patientService.findByPNC(pnc);

        if(patient == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/patient/", method = RequestMethod.POST)
    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
        Patient newPatient;

        try {
            newPatient = this.patientService.savePatient(patient);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/patient/id={id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePatient(@PathVariable("id") int id, @RequestBody Patient patient) {
        Patient currentPatient = this.patientService.findById(id);

        if (currentPatient == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        try {
            currentPatient = this.patientService.updatePatient(patient);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(currentPatient, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/patient/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePatient(@PathVariable("id") int id) {
        Patient patient = this.patientService.findById(id);

        if (patient == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        this.patientService.deletePatientById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
