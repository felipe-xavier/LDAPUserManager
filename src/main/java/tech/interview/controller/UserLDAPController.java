package tech.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.interview.model.UserLDAPModel;
import tech.interview.service.UserLDAPService;

import java.util.List;

@Controller
public class UserLDAPController {

    private final UserLDAPService userLDAPService;

    @Autowired
    public UserLDAPController(UserLDAPService userLDAPService) {
        this.userLDAPService = userLDAPService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserLDAPModel>> findAll() {
        return new ResponseEntity<>(userLDAPService.ldapUserFindAll(), HttpStatus.OK);
    }

    @GetMapping("/users/{uid}")
    public ResponseEntity<UserLDAPModel> findOne(@PathVariable(name = "uid") String uid) {
        return new ResponseEntity<>(userLDAPService.ldapUserFindOne(uid), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserLDAPModel> createUser(@RequestBody UserLDAPModel user) {
        UserLDAPModel result = userLDAPService.create(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<UserLDAPModel> UpdateUser(@RequestBody UserLDAPModel user) {
        UserLDAPModel result = userLDAPService.update(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/users/{uid}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "uid") String uid) {
        String result = userLDAPService.remove(uid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
