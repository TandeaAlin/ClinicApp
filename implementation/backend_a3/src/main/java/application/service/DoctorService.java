package application.service;

import application.model.Doctor;

import java.util.List;

public interface DoctorService {

    List<Doctor> findAllDoctors();

    Doctor findById(int id);

    public Doctor updateDoctor(Doctor doctor);
}
