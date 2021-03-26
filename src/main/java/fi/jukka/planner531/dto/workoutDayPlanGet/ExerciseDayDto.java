package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDayDto {
    private Long id;
    private Long exerciseBaseId;
    private String exerciseName;
    private int restTime;
    private List<ExerciseSetDto> exerciseSetDtos = new ArrayList<>();

    public ExerciseDayDto() {
    }

    public ExerciseDayDto(Long id, Long exerciseBaseId, String exerciseName, int restTime, List<ExerciseSetDto> exerciseSetDtos) {
        this.id = id;
        this.exerciseBaseId = exerciseBaseId;
        this.exerciseName = exerciseName;
        this.restTime = restTime;
        this.exerciseSetDtos = exerciseSetDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExerciseBaseId() {
        return exerciseBaseId;
    }

    public void setExerciseBaseId(Long exerciseBaseId) {
        this.exerciseBaseId = exerciseBaseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public List<ExerciseSetDto> getExerciseSets() {
        return exerciseSetDtos;
    }

    public void setExerciseSets(List<ExerciseSetDto> exerciseSetDtos) {
        this.exerciseSetDtos = exerciseSetDtos;
    }
}
