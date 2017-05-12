package application.controller;

import application.exceptions.DuplicateException;
import application.model.Doctor;
import application.model.User;
import application.repositories.DoctorRepository;
import application.service.DoctorService;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<?> listAllUsers() {
        List<User> users = this.userService.findAllUsers();

        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/id={id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        User user = this.userService.findById(id);

        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User newUser;
        try {
            newUser = this.userService.saveUser(user);
        } catch (DuplicateException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        User currentUser = this.userService.findById(id);

        if (currentUser == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentUser.getUsername().equals(username)){
            return new ResponseEntity<>("You cannot edit yourself",HttpStatus.BAD_REQUEST);
        }

        try {
            currentUser = this.userService.updateUser(user);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        User user = this.userService.findById(id);

        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (user.getUsername().equals(username)){
            return new ResponseEntity<>("You cannot delete yourself",HttpStatus.BAD_REQUEST);
        }

        if(user instanceof Doctor){
            ((Doctor) user).getWorkingHours().clear();
            this.doctorService.updateDoctor((Doctor) user);
            user.getRoles().clear();
            try {
                this.userService.updateUser(user);
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            } catch (DuplicateException e) {
                ;
            }
        }

        this.userService.deleteUserById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
