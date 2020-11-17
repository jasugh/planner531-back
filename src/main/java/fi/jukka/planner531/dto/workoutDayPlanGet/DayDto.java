package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayDto {
    private int day;
    private LocalDate workoutDate;
    private boolean completed;
    private List<ExerciseDay> exerciseDays = new ArrayList<>();

    public DayDto() {
    }

    public DayDto(int day, LocalDate workoutDate, boolean completed, List<ExerciseDay> exerciseDays) {
        this.day = day;
        this.workoutDate = workoutDate;
        this.completed = completed;
        this.exerciseDays = exerciseDays;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<ExerciseDay> getExerciseDays() {
        return exerciseDays;
    }

    public void setExerciseDays(List<ExerciseDay> exerciseDays) {
        this.exerciseDays = exerciseDays;
    }
}
