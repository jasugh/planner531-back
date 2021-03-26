package fi.jukka.planner531.dto;

import fi.jukka.planner531.dto.workoutDayPlanGet.ExerciseDayDto;

import java.time.LocalDate;
import java.util.List;

public class WorkoutDto {
    private Long id;
    private int cycle;
    private int week;
    private int day;

    private LocalDate workoutDate;
    private boolean assistanceAdded;
    private boolean completed;
    private List<ExerciseDayDto> exerciseDayDtos;

    public WorkoutDto() {
    }

    public WorkoutDto(Long id,
                      int cycle,
                      int week,
                      int day,
                      LocalDate workoutDate,
                      boolean assistanceAdded,
                      boolean completed,
                      List<ExerciseDayDto> exerciseDayDtos) {
        this.id = id;
        this.cycle = cycle;
        this.week = week;
        this.day = day;
        this.workoutDate = workoutDate;
        this.assistanceAdded = assistanceAdded;
        this.completed = completed;
        this.exerciseDayDtos = exerciseDayDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    public boolean isAssistanceAdded() {
        return assistanceAdded;
    }

    public void setAssistanceAdded(boolean assistanceAdded) {
        this.assistanceAdded = assistanceAdded;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<ExerciseDayDto> getExerciseDays() {
        return exerciseDayDtos;
    }

    public void setExerciseDays(List<ExerciseDayDto> exerciseDayDtos) {
        this.exerciseDayDtos = exerciseDayDtos;
    }
}
