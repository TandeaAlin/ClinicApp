package application.controller;

import application.model.Role;
import application.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleRestApiController {
    @Autowired
    private RoleService roleService;

    // Return a list containing all the users
    @RequestMapping(value = "/role/", method = RequestMethod.GET)
    public ResponseEntity<?> listAllUsers() {
        List<Role> users = this.roleService.findAllRoles();

        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Role>>(users, HttpStatus.OK);
    }
}
