package tech.interview.repository;

import org.springframework.stereotype.Repository;
import tech.interview.model.UserLDAPModel;

import java.util.List;

@Repository
public interface UserLDAPRepository {

    List<UserLDAPModel> findAll();
    UserLDAPModel findOne(String uid);
    UserLDAPModel create(UserLDAPModel user);
    UserLDAPModel update(UserLDAPModel user);
    String remove(String uid);
}
