package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayDto {
    private int day;
    private LocalDate workoutDate;
    private boolean completed;
    private List<ExerciseDayDto> exerciseDayDtos = new ArrayList<>();

    public DayDto() {
    }

    public DayDto(int day, LocalDate workoutDate, boolean completed, List<ExerciseDayDto> exerciseDayDtos) {
        this.day = day;
        this.workoutDate = workoutDate;
        this.completed = completed;
        this.exerciseDayDtos = exerciseDayDtos;
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

    public List<ExerciseDayDto> getExerciseDays() {
        return exerciseDayDtos;
    }

    public void setExerciseDays(List<ExerciseDayDto> exerciseDayDtos) {
        this.exerciseDayDtos = exerciseDayDtos;
    }
}
