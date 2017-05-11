package application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Doctor")
@PrimaryKeyJoinColumn(name="id")
public class Doctor extends User{

    public Doctor(String username, String password, String fullName, Set<Role> roles, Set<WorkingHour> workingHours) {
        super(username, password, fullName, roles);
        this.workingHours = workingHours;
    }

    public Doctor() {
    }

    @OneToMany(cascade = CascadeType.ALL, fetch  = FetchType.EAGER, mappedBy = "doctor", orphanRemoval = true)
    @JsonManagedReference
    Set<WorkingHour> workingHours;

    public Set<WorkingHour> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Set<WorkingHour> workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Doctor doctor = (Doctor) o;

        return workingHours != null ? workingHours.equals(doctor.workingHours) : doctor.workingHours == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (workingHours != null ? workingHours.hashCode() : 0);
        return result;
    }
}
