package application.service;

import application.model.Consultation;
import application.model.Doctor;
import application.model.Patient;
import application.model.builders.ConsultationBuilder;
import application.repositories.DoctorRepository;
import application.repositories.PatientRepository;
import application.repositories.RoleRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@Sql("data.sql")
public class ConsultationServiceTest {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PatientRepository patientRepository;

    private Doctor doctor1;
    private Doctor doctor2;
    private Patient patient1;
    private Patient patient2;

    private ConsultationBuilder consultationBuilder;
    private Consultation newConsultation;

    @Before
    public void setUp() throws Exception {
        this.doctor1 = this.doctorRepository.findAll().get(0);
        this.doctor2 = this.doctorRepository.findAll().get(1);
        this.patient1 = this.patientRepository.findAll().get(0);
        this.patient2 = this.patientRepository.findAll().get(1);

        this.consultationBuilder = new ConsultationBuilder();

        Date start = new Date(System.currentTimeMillis());
        start = DateUtils.addHours(start,1);

        this.newConsultation = this.consultationBuilder
                .setDoctor(this.doctor1)
                .setPatient(this.patient1)
                .setStartTime(start)
                .setEndTime(DateUtils.addMinutes(start,30))
                .createConsultation();

        this.newConsultation = this.consultationService.saveConsultation(this.newConsultation);
    }

    @After
    public void tearDown() throws Exception {
        for(Consultation c: this.consultationService.findAll()){
            this.consultationService.deleteConsultationById(c.getId());
        }

        this.doctorRepository.deleteAll();
        this.patientRepository.deleteAll();
        this.roleRepository.deleteAll();
    }

    @Test
    public void findById() throws Exception {
        Consultation consultation = this.consultationService.findById(this.newConsultation.getId());

        assertEquals(this.newConsultation,consultation);
    }

    @Test
    public void saveConsultation() throws Exception {
        Date start = new Date(System.currentTimeMillis());
        start = DateUtils.addDays(start,1);

        Consultation consultation = this.consultationBuilder
                .setDoctor(this.doctor1)
                .setPatient(this.patient1)
                .setStartTime(start)
                .setEndTime(DateUtils.addMinutes(start,15))
                .createConsultation();

        Consultation created = this.consultationService.saveConsultation(consultation);

        assertNotNull(created);
    }

    @Test
    public void updateConsultation() throws Exception {
        Consultation consultation = this.consultationService.findById(this.newConsultation.getId());
        assertEquals(this.newConsultation,consultation);

        consultation.setDoctor(this.doctor2);
        consultation.setPatient(this.patient2);
        consultation.setStartTime(DateUtils.addDays(consultation.getStartTime(),1));
        consultation.setEndTime(DateUtils.addDays(consultation.getEndTime(),1));

        this.consultationService.updateConsultation(consultation);
        Consultation updated = this.consultationService.findById(this.newConsultation.getId());

        assertEquals(consultation,updated);

    }

    @Test
    public void deleteConsultationById() throws Exception {
        Consultation consultation = this.consultationService.findById(this.newConsultation.getId());
        assertNotNull(consultation);

        this.consultationService.deleteConsultationById(this.newConsultation.getId());

        consultation = this.consultationService.findById(this.newConsultation.getId());
        assertNull(consultation);

    }

}