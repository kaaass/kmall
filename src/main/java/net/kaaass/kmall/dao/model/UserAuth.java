package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_auth")
public class UserAuth {
    @Id
    private String id;

    private byte[] enable;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    private String password;

    private String phone;

    @Column(name = "register_time")
    private Date registerTime;

    private String roles;

    private byte[] validate;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return enable
     */
    public byte[] getEnable() {
        return enable;
    }

    /**
     * @param enable
     */
    public void setEnable(byte[] enable) {
        this.enable = enable;
    }

    /**
     * @return last_login_time
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * @return register_time
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * @param registerTime
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * @return roles
     */
    public String getRoles() {
        return roles;
    }

    /**
     * @param roles
     */
    public void setRoles(String roles) {
        this.roles = roles == null ? null : roles.trim();
    }

    /**
     * @return validate
     */
    public byte[] getValidate() {
        return validate;
    }

    /**
     * @param validate
     */
    public void setValidate(byte[] validate) {
        this.validate = validate;
    }
}