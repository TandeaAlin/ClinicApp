package application.repositories;

import application.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>{

    List<Patient> findByFullName(String fullName);

    Patient findByPersonalNumericalCode(String personalNumericalCode);
}
