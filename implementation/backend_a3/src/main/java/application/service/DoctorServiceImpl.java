package application.service;


import application.model.Doctor;
import application.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("doctorService")
public class DoctorServiceImpl implements DoctorService{

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAllDoctors() {
        return this.doctorRepository.findAll();
    }

    public Doctor findById(int id) {
        return this.doctorRepository.findOne(id);
    }

    public Doctor updateDoctor(Doctor doctor){
        Doctor doc = this.doctorRepository.findOne(doctor.getId());

        if(doctor.getPassword()==null){
            doctor.setPassword(doc.getPassword());
        }

        return this.doctorRepository.save(doctor);
    }
}
