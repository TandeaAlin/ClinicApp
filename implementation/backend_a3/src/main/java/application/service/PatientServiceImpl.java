package application.service;

import application.exceptions.DuplicateException;
import application.exceptions.InvalidDataException;
import application.model.Patient;
import application.repositories.PatientRepository;
import application.validators.PatientValidator;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientValidator patientValidator;

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

    public Patient savePatient(Patient patient) throws InvalidDataException, DuplicateException {
        if(this.patientRepository.findByPersonalNumericalCode(patient.getPersonalNumericalCode())!=null){
            throw new DuplicateException("This PNC belongs to an existing patient");
        }

        if(!this.patientValidator.validatePersonalNumericalCode(patient.getPersonalNumericalCode())){
            throw new InvalidDataException("Invalid PNC.");
        }

        if(!this.patientValidator.validateIdSeries(patient.getIdCardSeries())){
            throw new InvalidDataException("Invalid ID Card Series.");
        }

        if(!this.patientValidator.validateIdCardNumber(patient.getIdCardNumber())){
            throw new InvalidDataException("Invalid ID Card number.");
        }

        return this.patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) throws InvalidDataException, DuplicateException {
        Patient p = this.patientRepository.findByPersonalNumericalCode(patient.getPersonalNumericalCode());

        if (p!= null && p.getId() != patient.getId()){
            throw new DuplicateException("This PNC belongs to an existing client");
        }

        if(!this.patientValidator.validatePersonalNumericalCode(patient.getPersonalNumericalCode())){
            throw new InvalidDataException("Invalid PNC.");
        }

        if(!this.patientValidator.validateIdSeries(patient.getIdCardSeries())){
            throw new InvalidDataException("Invalid ID Card Series.");
        }

        if(!this.patientValidator.validateIdCardNumber(patient.getIdCardNumber())){
            throw new InvalidDataException("Invalid ID Card number.");
        }

        return this.patientRepository.save(patient);
    }

    public void deletePatientById(int id) {
        this.patientRepository.delete(id);
    }
}
