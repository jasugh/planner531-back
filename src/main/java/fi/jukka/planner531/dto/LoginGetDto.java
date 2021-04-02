package fi.jukka.planner531.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginGetDto {
    private long id;
    private String loginName;
    private Date passwordChanged;
    private List<RoleDto> roles = new ArrayList<>();
    private long mainExerciseHeaderId;
    private Long startingDetailsId;
    private Long workoutDayPlanId;

    private Date created;

    public LoginGetDto() {
    }

    public LoginGetDto(
            long id,
            String loginName,
            List<RoleDto> roles,
            Long mainExerciseHeaderId,
            Long startingDetailsId,
            Long workoutDayPlanId) {
        this.id = id;
        this.loginName = loginName;
        this.roles = roles;
        this.mainExerciseHeaderId = mainExerciseHeaderId;
        this.startingDetailsId = startingDetailsId;
        this.workoutDayPlanId = workoutDayPlanId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public Date getPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(Date passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public long getMainExerciseHeaderId() {
        return mainExerciseHeaderId;
    }

    public void setMainExerciseHeaderId(long mainExerciseHeaderId) {
        this.mainExerciseHeaderId = mainExerciseHeaderId;
    }

    public Long getStartingDetailsId() {
        return startingDetailsId;
    }

    public void setStartingDetailsId(Long startingDetailsId) {
        this.startingDetailsId = startingDetailsId;
    }

    public Long getWorkoutDayPlanId() {
        return workoutDayPlanId;
    }

    public void setWorkoutDayPlanId(Long workoutDayPlanId) {
        this.workoutDayPlanId = workoutDayPlanId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
