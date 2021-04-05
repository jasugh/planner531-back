package fi.jukka.planner531.dto;

import java.time.LocalDate;
import java.util.Date;

public class LoginDto {
    private Long id;
    private String loginName;
    private String password;
    private Date passwordChanged;
    private Date changed;
    private Date created;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(Date passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
