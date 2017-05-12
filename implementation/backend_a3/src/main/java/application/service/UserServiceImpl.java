package application.service;

import application.exceptions.DuplicateException;
import application.model.Doctor;
import application.model.User;
import application.repositories.UserRepository;
import application.security.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public User findById(int id) {
        return this.userRepository.findOne(id);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public User findByToken(String token) {
        String username = this.jwtUtils.getUsernameFromToken(token);
        return this.userRepository.findByUsername(username);
    }

    public User saveUser(User user) throws DuplicateException{
        if(this.userRepository.findByUsername(user.getUsername())!=null){
            throw new DuplicateException("Username already exists.");
        }

        return this.userRepository.save(user);
    }

    public User updateUser(User user) throws DuplicateException{
        User u_username = this.userRepository.findByUsername(user.getUsername()); // User with the username the new user will have

        // This will throw an exception when you're trying to change the username to an already used one
        // But only if the username belongs to a different user
        if( u_username != null && u_username.getId()!=user.getId()){
            throw new DuplicateException("Username already exists.");
        }

        // If the user has no password field then use the already existing data
        if (user.getPassword() == null){
            User u = this.userRepository.findOne(user.getId());
            user.setPassword(u.getPassword());
        }

        if(u_username == null){
            u_username = this.userRepository.findOne(user.getId());
        }

        if(u_username instanceof Doctor){
            BeanUtils.copyProperties(user,u_username);
            return this.userRepository.save(u_username);
        }

        return this.userRepository.save(user);
    }

    public void deleteUserById(int id) {
        this.userRepository.delete(id);
    }
}
