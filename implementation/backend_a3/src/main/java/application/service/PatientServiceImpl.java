package application.service;

import application.model.Patient;
import application.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    public Patient findById(int id) {
        return this.patientRepository.findOne(id);
    }

    public List<Patient> findAllPatients() {
        return this.patientRepository.findAll();
    }

    public List<Patient> findByFullName(String fullName) {
        return this.patientRepository.findByFullName(fullName);
    }

    public Patient findByPNC(String pnc) {
        return this.patientRepository.findByPersonalNumericalCode(pnc);
    }

    public Patient savePatient(Patient patient) {
        return this.patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        return this.patientRepository.save(patient);
    }

    public void deletePatientById(int id) {
        this.patientRepository.delete(id);
    }
}
