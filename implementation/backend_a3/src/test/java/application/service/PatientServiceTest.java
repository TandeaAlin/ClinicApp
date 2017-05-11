package application.service;

import application.model.Patient;
import application.model.builders.PatientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    private PatientBuilder builder;
    private Patient newPatient;

    @Before
    public void setUp() throws Exception {
        this.builder = new PatientBuilder();

        this.newPatient = this.builder
                .setFullName("Test Name")
                .setAddress("Test address")
                .setPersonalNumericalCode("1970402203036")
                .setIdCardSeries("XB")
                .setIdCardNumber("123456")
                .createPatient();

        this.newPatient = this.patientService.savePatient(this.newPatient);
    }

    @After
    public void tearDown() throws Exception {
        for(Patient patient: this.patientService.findAllPatients()){
            this.patientService.deletePatientById(patient.getId());
        }
    }

    @Test
    public void findById() throws Exception {
        Patient patient = this.patientService.findById(this.newPatient.getId());

        assertEquals(this.newPatient,patient);
    }

    @Test
    public void savePatient() throws Exception {
        Patient patient = this.builder
                .setFullName("New Full Name")
                .setAddress("Random Address Nr. 127")
                .setPersonalNumericalCode("1970208307432")
                .setIdCardSeries("GG")
                .setIdCardNumber("825383")
                .createPatient();

        Patient created = this.patientService.savePatient(patient);

        assertNotNull(created);
    }

    @Test
    public void updatePatient() throws Exception {
        Patient patient = this.patientService.findById(this.newPatient.getId());
        assertEquals(this.newPatient,patient);

        patient.setFullName("Another name");
        patient.setAddress("Other address");
        patient.setIdCardSeries("SX");
        patient.setIdCardNumber("517257");
        patient.setPersonalNumericalCode("2960821225241");

        this.patientService.updatePatient(patient);
        Patient updated = this.patientService.findById(this.newPatient.getId());

        assertEquals(patient,updated);

    }

    @Test
    public void deletePatientById() throws Exception {
        Patient patient = this.patientService.findById(this.newPatient.getId());
        assertNotNull(patient);

        this.patientService.deletePatientById(this.newPatient.getId());

        patient = this.patientService.findById(this.newPatient.getId());
        assertNull(patient);
    }

}