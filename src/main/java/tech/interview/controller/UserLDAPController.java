package tech.interview.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.interview.model.UserLDAPModel;
import tech.interview.service.UserLDAPService;

import java.util.List;

@RestController
@Api(value = "API REST LDAP")
@CrossOrigin(origins = "*")
public class UserLDAPController {

    private final UserLDAPService userLDAPService;

    @Autowired
    public UserLDAPController(UserLDAPService userLDAPService) {
        this.userLDAPService = userLDAPService;
    }

    @GetMapping("/users")
    @ApiOperation(value = "Return all the users on the LDAP Database.")
    public ResponseEntity<List<UserLDAPModel>> findAll() {
        return new ResponseEntity<>(userLDAPService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/users/{uid}")
    @ApiOperation(value = "Return a single user uid on the LDAP Database.")
    public ResponseEntity<UserLDAPModel> findOne(@PathVariable(name = "uid") String uid) {
        UserLDAPModel user = userLDAPService.findOne(uid);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @PostMapping("/users")
    @ApiOperation(value = "Create a User on the LDAP Database.")
    public ResponseEntity<UserLDAPModel> createUser(@RequestBody UserLDAPModel user) {
        UserLDAPModel result = userLDAPService.create(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/users")
    @ApiOperation(value = "Update a User on the LDAP Database.")
    public ResponseEntity<String> UpdateUser(@RequestBody UserLDAPModel user) {
        String result = userLDAPService.update(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/users/{uid}")
    @ApiOperation(value = "Delete a User uid on the LDAP Database.")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "uid") String uid) {
        String result = userLDAPService.remove(uid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
