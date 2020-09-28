package tech.interview.model;

public class UserLDAPModel {
    private String uid;
    private String cn;
    private String sn;

    public String getUid() {
        return uid;
    }

    public String getCn() {
        return cn;
    }

    public String getSn() {
        return sn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
