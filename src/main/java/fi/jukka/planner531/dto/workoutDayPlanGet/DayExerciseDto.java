package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.util.ArrayList;
import java.util.List;

public class DayExerciseDto {
    private String exercise;
    private List<SetDto> setDtos = new ArrayList<>();

    public DayExerciseDto() {
    }

    public DayExerciseDto(String exercise, List<SetDto> setDtos) {
        this.exercise = exercise;
        this.setDtos = setDtos;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public List<SetDto> getSetDtos() {
        return setDtos;
    }

    public void setSetDtos(List<SetDto> setDtos) {
        this.setDtos = setDtos;
    }
}
