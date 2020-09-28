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

    @GetMapping("/Users")
    public ResponseEntity<List<UserLDAPModel>> retrieveAll() {
        return new ResponseEntity<>(userLDAPService.ldapUserFindAll(), HttpStatus.OK);
    }

    @GetMapping("/Users/{uid}")
    public ResponseEntity<UserLDAPModel> retrieveOne(@PathVariable(name = "uid") String uid) {
        return new ResponseEntity<>(userLDAPService.ldapUserFindOne(uid), HttpStatus.OK);
    }

    @PostMapping("/Users")
    public ResponseEntity<String> bindLdapPerson(@RequestBody UserLDAPModel user) {
        String result = userLDAPService.create(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/Users")
    public ResponseEntity<String> rebindLdapPerson(@RequestBody UserLDAPModel user) {
        String result = userLDAPService.update(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/Users/{uid}")
    public ResponseEntity<String> unbindLdapPerson(@PathVariable(name = "uid") String uid) {
        String result = userLDAPService.remove(uid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
