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

/** Repository Implementation Class for the User.
 *
 */
@Repository
public class UserLDAPRepositoryImpl implements UserLDAPRepository {

    private LdapTemplate ldapTemplate;

    public UserLDAPRepositoryImpl(){}

    @Autowired
    public UserLDAPRepositoryImpl(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    /**
     *
     * @param uid Identifier for a specific user.
     * @return A Name as a DistinguishedName for the user.
     */
    public Name buildDn(String uid) {
        return LdapNameBuilder.newInstance().add("uid", uid).build();
    }

    /** Creates the attributes for the User in a LDAP database.
     *
     * @param user UserLDAPModel object
     * @return The Attributes for the User.
     */
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

    /** Creates a User in the LDAP Database
     *
     * @param user UserLDAPModel object
     * @return The User just created.
     */
    @Override
    public UserLDAPModel create(UserLDAPModel user) {
        Name dn = buildDn(user.getUid());
        Attributes attrs = buildAttributes(user);
        ldapTemplate.bind(dn, null, attrs);
        return user;
    }

    /** Updates a User on the LDAP Database
     * @param user UserLDAPModel object
     * @return Return the User object after the update succeed
     */
    @Override
    public String update(UserLDAPModel user) {
        Name dn = buildDn(user.getUid());
        try {
            UserLDAPModel user_ = ldapTemplate.lookup(dn, new PersonAttributeMapper());
            Attributes attrs = buildAttributes(user);
            ldapTemplate.rebind(dn, null, attrs);
            return "User " + user.toString() + " updated successfully";
        } catch (org.springframework.ldap.NameNotFoundException e) {
            return "No user " + user.getUid() + " found.";
        }
    }

    /** Removes a User from the LDAP Database given uid.
     *
     * @param uid Identifier for a specific user.
     * @return A string for success
     */
    @Override
    public String remove(String uid) {
        Name dn = buildDn(uid);
        try {
            UserLDAPModel user = ldapTemplate.lookup(dn, new PersonAttributeMapper());
            ldapTemplate.unbind(dn);
            return user.getCn() + " removed successfully";
        } catch (org.springframework.ldap.NameNotFoundException e) {
            return "No user " + uid + " found.";
        }
    }

    /** Retrieve all the Users stored in the LDAP Database.
     *
     * @return All the Users stored in the LDAP Database.
     */
    @Override
    public List<UserLDAPModel> findAll() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return ldapTemplate.search(query().where("objectclass").is("inetOrgPerson"),
                new PersonAttributeMapper());
    }

    /** Retrieve a specific User uid stored in the LDAP Database.
     *
     * @param uid Identifier for a specific user.
     * @return A UserLDAPModel object with the attributes collected.
     */
    @Override
    public UserLDAPModel findOne(String uid) {
        Name dn = buildDn(uid);
        try {
            return ldapTemplate.lookup(dn, new PersonAttributeMapper());
        } catch (org.springframework.ldap.NameNotFoundException e) {
            UserLDAPModel user = new UserLDAPModel();
            user.setSn("");
            user.setCn("");
            user.setUid("");
            return user;
        }
    }

    /**
     * A class with an overridden method to map the LDAP user attributes to a UserLDAPModel object.
     */
    private static class PersonAttributeMapper implements AttributesMapper<UserLDAPModel> {

        /**
         *
         * @param attributes Attributes given to be mapped.
         * @return A LDAPUserModel object filled by attributes.
         * @throws NamingException Catch error and throws this exception.
         */
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
