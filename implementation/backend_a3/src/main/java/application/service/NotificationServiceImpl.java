package application.service;

import application.model.Consultation;
import application.model.Doctor;
import application.model.Notification;
import application.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ConsultationService consultationService;


    public Notification findByConsultation(Consultation consultation) {
        return this.notificationRepository.findByConsultation(consultation);
    }

    public List<Notification> findByDoctor(Doctor doctor) {
        List<Consultation> consultations = this.consultationService.findByDoctor(doctor);

        List<Notification> notifications = consultations.parallelStream()
                .map(c -> this.notificationRepository.findByConsultation(c))
                .filter(n -> n!=null)
                .collect(Collectors.toList());

        return notifications;
    }

    public List<Notification> findNewByDoctor(Doctor doctor) {
        List<Consultation> consultations = this.consultationService.findByDoctor(doctor);

        List<Notification> notifications = consultations.parallelStream()
                .map(c -> this.notificationRepository.findByConsultation(c))
                .filter(n -> n!=null)
                .filter(n -> !n.isSeen())
                .collect(Collectors.toList());

        return notifications;
    }

    public Notification saveNotification(Notification notification) {
        return this.notificationRepository.save(notification);
    }

    public Notification updateNotification(Notification notification) {
        return this.saveNotification(notification);
    }
}
