package fi.jukka.planner531.dto;

import java.util.ArrayList;
import java.util.List;

public class LoginGetDto {
    private long id;
    private String loginName;
    private List<RoleDto> roles = new ArrayList<>();
    private long mainExercisesId;
    private Long startingDetailsId;
    private Long workoutDayPlanId;

    public LoginGetDto() {
    }

    public LoginGetDto(
            long id,
            String loginName,
            List<RoleDto> roles,
            Long mainExercisesId,
            Long startingDetailsId,
            Long workoutDayPlanId) {
        this.id = id;
        this.loginName = loginName;
        this.roles = roles;
        this.mainExercisesId = mainExercisesId;
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

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public long getMainExercisesId() {
        return mainExercisesId;
    }

    public void setMainExercisesId(long mainExercisesId) {
        this.mainExercisesId = mainExercisesId;
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
}
