package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDay {
    private Long id;
    private Long exerciseBaseId;
    private String exerciseName;
    private List<ExerciseSet> exerciseSets = new ArrayList<>();

    public ExerciseDay() {
    }

    public ExerciseDay(Long id, Long exerciseBaseId, String exerciseName, List<ExerciseSet> exerciseSets) {
        this.id = id;
        this.exerciseBaseId = exerciseBaseId;
        this.exerciseName = exerciseName;
        this.exerciseSets = exerciseSets;
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

    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void setExerciseSets(List<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
    }
}
