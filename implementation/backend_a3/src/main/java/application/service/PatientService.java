package application.service;

import application.exceptions.DuplicateException;
import application.exceptions.InvalidDataException;
import application.model.Patient;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;

public interface PatientService {
    Patient findById(int id);

    List<Patient> findAllPatients();

    List<Patient> findByFullName(String fullName);

    Patient findByPNC(String pnc);

    Patient savePatient(Patient patient) throws InvalidDataException, DuplicateException;

    Patient updatePatient(Patient patient) throws InvalidDataException, DuplicateException;

    void deletePatientById(int id);
}
