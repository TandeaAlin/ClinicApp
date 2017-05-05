package application.controller;

import application.model.Patient;
import application.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PatientRestApiController {

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/patient/", method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        List<Patient> patients = this.patientService.findAllPatients();

        if (patients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @RequestMapping(value = "/patient/id={id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable("id") int id) {
        Patient patient = this.patientService.findById(id);

        if (patient == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @RequestMapping(value = "/patient/pnc={pnc}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientByPnc(@PathVariable("pnc") String pnc){
        Patient patient = this.patientService.findByPNC(pnc);

        if(patient == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @RequestMapping(value = "/patient/", method = RequestMethod.POST)
    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
        Patient newPatient;

        newPatient = this.patientService.savePatient(patient);

        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/patient/id={id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePatient(@PathVariable("id") int id, @RequestBody Patient patient) {
        Patient currentPatient = this.patientService.findById(id);

        if (currentPatient == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        currentPatient = this.patientService.updatePatient(patient);

        return new ResponseEntity<>(currentPatient, HttpStatus.OK);
    }

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
