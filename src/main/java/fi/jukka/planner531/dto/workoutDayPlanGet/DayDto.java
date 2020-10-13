package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayDto {
    private int day;
    private String exercise;
    private LocalDate workoutDate;
    private boolean completed;
    private List<SetDto> setDtos = new ArrayList<>();

    public DayDto() {
    }

    public DayDto(int day, String exercise, LocalDate workoutDate, boolean completed, List<SetDto> setDtos) {
        this.day = day;
        this.exercise = exercise;
        this.workoutDate = workoutDate;
        this.completed = completed;
        this.setDtos = setDtos;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<SetDto> getSetDtos() {
        return setDtos;
    }

    public void setSetDtos(List<SetDto> setDtos) {
        this.setDtos = setDtos;
    }
}
