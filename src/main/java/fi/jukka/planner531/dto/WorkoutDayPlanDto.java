package fi.jukka.planner531.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import fi.jukka.planner531.model.StartingDetails;
import fi.jukka.planner531.model.WorkoutDay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDayPlanDto {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startingDate;
    private Long loginId;
    private String loginLoginName;
    private List<WorkoutDay> workoutDays = new ArrayList<>();

    public WorkoutDayPlanDto() {
    }

    public WorkoutDayPlanDto(Long id, LocalDate startingDate, Long loginId, String loginLoginName, List<WorkoutDay> workoutDays) {
        this.id = id;
        this.startingDate = startingDate;
        this.loginId = loginId;
        this.loginLoginName = loginLoginName;
        this.workoutDays = workoutDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getLoginLoginName() {
        return loginLoginName;
    }

    public void setLoginLoginName(String loginLoginName) {
        this.loginLoginName = loginLoginName;
    }

    public List<WorkoutDay> getWorkoutDays() {
        return workoutDays;
    }

    public void setWorkoutDays(List<WorkoutDay> workoutDays) {
        this.workoutDays = workoutDays;
    }
}
