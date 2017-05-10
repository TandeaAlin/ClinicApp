package application.service;

import application.model.Consultation;
import application.model.Doctor;
import application.model.Notification;

import java.util.List;

public interface NotificationService {

    Notification findByConsultation(Consultation consultation);

    List<Notification> findByDoctor(Doctor doctor);

    List<Notification> findNewByDoctor(Doctor doctor);

    Notification saveNotification(Notification notification);

    Notification updateNotification(Notification notification);
}
