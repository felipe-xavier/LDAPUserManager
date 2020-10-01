package tech.interview.repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;

import tech.interview.model.UserLDAPModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserLDAPRepositoryImpl implements UserLDAPRepository {

    private LdapTemplate ldapTemplate;

    public UserLDAPRepositoryImpl(){}

    @Autowired
    public UserLDAPRepositoryImpl(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public Name buildDn(String uid) {
        return LdapNameBuilder.newInstance().add("uid", uid).build();
    }

    private Attributes buildAttributes(UserLDAPModel user) {

        BasicAttribute objClassAttr = new BasicAttribute("objectclass");
        objClassAttr.add("top");
        objClassAttr.add("inetOrgPerson");

        Attributes attrs = new BasicAttributes();
        attrs.put(objClassAttr);
        attrs.put("uid", user.getUid());
        attrs.put("cn", user.getCn());
        attrs.put("sn", user.getSn());
        return attrs;
    }

    @Override
    public UserLDAPModel create(UserLDAPModel user) {
        Name dn = buildDn(user.getUid());
        Attributes attrs = buildAttributes(user);
        ldapTemplate.bind(dn, null, attrs);
        return user;
    }

    @Override
    public UserLDAPModel update(UserLDAPModel user) {
        Name dn = buildDn(user.getUid());
        Attributes attrs = buildAttributes(user);
        ldapTemplate.rebind(dn, null, attrs);
        return user;
    }

    @Override
    public String remove(String uid) {
        Name dn = buildDn(uid);
        ldapTemplate.unbind(dn);
        return uid + " removed successfully";
    }

    @Override
    public List<UserLDAPModel> findAll() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return ldapTemplate.search(query().where("objectclass").is("inetOrgPerson"),
                new PersonAttributeMapper());
    }

    @Override
    public UserLDAPModel findOne(String uid) {
        Name dn = buildDn(uid);
        return ldapTemplate.lookup(dn, new PersonAttributeMapper());
    }

    private static class PersonAttributeMapper implements AttributesMapper<UserLDAPModel> {

        @Override
        public UserLDAPModel mapFromAttributes(Attributes attributes) throws NamingException {
            UserLDAPModel user = new UserLDAPModel();
            user.setUid(attributes.get("uid") != null ? attributes.get("uid").get().toString() : null);
            user.setCn(attributes.get("cn") != null ? attributes.get("cn").get().toString() : null);
            user.setSn(attributes.get("sn") != null ? attributes.get("sn").get().toString() : null);

            return user;
        }
    }

}
