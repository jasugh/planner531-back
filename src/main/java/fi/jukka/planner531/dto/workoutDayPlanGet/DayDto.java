package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayDto {
    private int day;
    private LocalDate workoutDate;
    private boolean completed;
    private List<DayExerciseDto> dayExerciseDtos = new ArrayList<>();

    public DayDto() {
    }

    public DayDto(int day, LocalDate workoutDate, boolean completed, List<DayExerciseDto> dayExerciseDtos) {
        this.day = day;
        this.workoutDate = workoutDate;
        this.completed = completed;
        this.dayExerciseDtos = dayExerciseDtos;
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

    public List<DayExerciseDto> getDayExerciseDtos() {
        return dayExerciseDtos;
    }

    public void setDayExerciseDtos(List<DayExerciseDto> dayExerciseDtos) {
        this.dayExerciseDtos = dayExerciseDtos;
    }
}
