package application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="User")
@Inheritance(strategy=InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name="username", unique=true, nullable=false)
    private String username;

    @NotBlank
    @JsonIgnore
    @Column(name="password", nullable=false)
    private String password;

    @NotBlank
    @Column(name="fullName", nullable=false)
    private String fullName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="User_has_Role",
            joinColumns = { @JoinColumn(name = "User_id")},
            inverseJoinColumns = { @JoinColumn(name = "Role_Id")}
    )
    private Set<Role> roles = new HashSet<Role>();

    public User() {
    }

    public User(String username, String password, String fullName, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
