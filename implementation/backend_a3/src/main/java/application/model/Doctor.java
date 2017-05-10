package application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Doctor")
@PrimaryKeyJoinColumn(name="id")
public class Doctor extends User{

    @OneToMany(cascade = CascadeType.ALL, fetch  = FetchType.EAGER, mappedBy = "doctor", orphanRemoval = true)
    @JsonManagedReference
    Set<WorkingHour> workingHours;

    public Set<WorkingHour> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Set<WorkingHour> workingHours) {
        this.workingHours = workingHours;
    }
}
