package application.controller;

import application.model.Doctor;
import application.model.WorkingHour;
import application.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DoctorRestApiController {

    @Autowired
    private DoctorService doctorService;

    // Return a list containing all the users
    @RequestMapping(value = "/doctor/", method = RequestMethod.GET)
    public ResponseEntity<?> listAllDoctors() {
        List<Doctor> doctors = this.doctorService.findAllDoctors();

        if (doctors.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    // Return a doctor by id
    @RequestMapping(value = "/doctor/id={id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        Doctor doctor = this.doctorService.findById(id);

        if (doctor == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctor/id={id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDoctor(@PathVariable("id") int id, @RequestBody Doctor doctor) {
        Doctor current = this.doctorService.findById(id);

        if (current == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        current = this.doctorService.updateDoctor(doctor);


        return new ResponseEntity<>(current, HttpStatus.OK);
    }
}
