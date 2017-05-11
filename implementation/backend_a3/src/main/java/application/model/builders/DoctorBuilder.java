package application.model.builders;

import application.model.Doctor;
import application.model.Role;
import application.model.WorkingHour;

import java.util.HashSet;
import java.util.Set;

public class DoctorBuilder {
    private String username;
    private String password;
    private String fullName;
    private Set<Role> roles = new HashSet<>();
    private Set<WorkingHour> workingHours = new HashSet<>();

    public DoctorBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public DoctorBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public DoctorBuilder setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public DoctorBuilder addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public DoctorBuilder addWorkingHour(WorkingHour workingHour) {
        this.workingHours.add(workingHour);
        return this;
    }

    public Doctor createDoctor() {
        return new Doctor(username, password, fullName, roles, workingHours);
    }
}