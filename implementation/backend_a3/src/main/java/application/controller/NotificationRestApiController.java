package application.controller;

import application.model.Consultation;
import application.model.Doctor;
import application.model.Notification;
import application.model.builders.NotificationBuilder;
import application.service.ConsultationService;
import application.service.DoctorService;
import application.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationRestApiController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ConsultationService consultationService;

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = "/notification/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Doctor doctor = this.doctorService.findByUsername(auth.getName());

        List<Notification> notificationList = this.notificationService.findByDoctor(doctor);

        if (notificationList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = "/notification/new", method = RequestMethod.GET)
    public ResponseEntity<?> getNewNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Doctor doctor = this.doctorService.findByUsername(auth.getName());

        List<Notification> notificationList = this.notificationService.findNewByDoctor(doctor);

        if (notificationList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    @RequestMapping(value = "/notification/", method = RequestMethod.POST)
    public ResponseEntity<?> createConsultation(@RequestBody Consultation consultation) {
        Consultation consultation1 = this.consultationService.findById(consultation.getId());

        if ( consultation1 == null){
            return new ResponseEntity<>("Invalid consultation",HttpStatus.BAD_REQUEST);
        }

        Notification notification = this.notificationService.findByConsultation(consultation);

        if ( notification != null){
            return new ResponseEntity<>("The doctor was already notified", HttpStatus.BAD_REQUEST);
        }


        NotificationBuilder builder = new NotificationBuilder();


        notification = builder.setConsultation(consultation1)
                .setCreatedAt(new Date(System.currentTimeMillis()))
                .setText("One of your patients has arrived")
                .createNotification();

        notification = this.notificationService.saveNotification(notification);

        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = "/notification/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateConsultation(@PathVariable("id") int id, @RequestBody Notification notification) {
        Notification updated = this.notificationService.updateNotification(notification);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
