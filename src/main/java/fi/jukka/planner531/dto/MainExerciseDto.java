package fi.jukka.planner531.dto;

import fi.jukka.planner531.model.MainExerciseHeader;

public class MainExerciseDto {
    private Long id;
    private String name;
    private int restTime;
    private float weightIncrement;
    private float oneRmKg;
    private int oneRmReps;
    private float oneRm;
    private String notes;


    public MainExerciseDto() {
    }

    public MainExerciseDto(
            Long id,
            String name,
            int restTime,
            float weightIncrement,
            float oneRmKg,
            int oneRmReps,
            float oneRm,
            String notes
    ) {
        this.id = id;
        this.name = name;
        this.restTime = restTime;
        this.weightIncrement = weightIncrement;
        this.oneRmKg = oneRmKg;
        this.oneRmReps = oneRmReps;
        this.oneRm = oneRm;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public float getWeightIncrement() {
        return weightIncrement;
    }

    public void setWeightIncrement(float weightIncrement) {
        this.weightIncrement = weightIncrement;
    }

    public float getOneRmKg() {
        return oneRmKg;
    }

    public void setOneRmKg(float oneRmKg) {
        this.oneRmKg = oneRmKg;
    }

    public int getOneRmReps() {
        return oneRmReps;
    }

    public void setOneRmReps(int oneRmReps) {
        this.oneRmReps = oneRmReps;
    }

    public float getOneRm() {
        return oneRm;
    }

    public void setOneRm(float oneRm) {
        this.oneRm = oneRm;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
