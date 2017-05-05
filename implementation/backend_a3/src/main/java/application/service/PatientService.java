package application.service;

import application.model.Patient;

import java.util.List;

public interface PatientService {
    Patient findById(int id);

    List<Patient> findAllPatients();

    List<Patient> findByFullName(String fullName);

    Patient findByPNC(String pnc);

    Patient savePatient(Patient patient);

    Patient updatePatient(Patient patient);

    void deletePatientById(int id);
}
