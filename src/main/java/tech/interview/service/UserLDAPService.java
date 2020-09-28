package tech.interview.service;

import tech.interview.model.UserLDAPModel;
import tech.interview.repository.UserLDAPRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLDAPService {
    
    private final UserLDAPRepositoryImpl userLDAPRepositoryImpl;

    @Autowired
    public UserLDAPService(UserLDAPRepositoryImpl userLDAPRepositoryImpl) {
        this.userLDAPRepositoryImpl = userLDAPRepositoryImpl;
    }

    public List<UserLDAPModel> ldapUserFindAll() {
        return userLDAPRepositoryImpl.findAll();
    }

    public UserLDAPModel ldapUserFindOne(String uid) {
        return userLDAPRepositoryImpl.findOne(uid);
    }

    public String create(UserLDAPModel user) {
        return userLDAPRepositoryImpl.create(user);
    }

    public String update(UserLDAPModel user) {
        return userLDAPRepositoryImpl.update(user);
    }

    public String remove(String uid) {
        return userLDAPRepositoryImpl.remove(uid);
    }
}
