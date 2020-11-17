package fi.jukka.planner531.dto;

import fi.jukka.planner531.model.AssistanceExercise;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class MainExerciseDto {
    private Long id;
    private String name;
    private int restTime;
    private float weightIncrement;
    private float oneRmKg;
    private int oneRmReps;
    private float oneRm;
    private String notes;
    private int exerciseNumber;
    private Long mainExerciseHeaderId;
    private List<AssistanceExercise>assistanceExercises = new ArrayList<>();

    public MainExerciseDto() {
    }

    public MainExerciseDto(Long id,
                           String name,
                           int restTime,
                           float weightIncrement,
                           float oneRmKg,
                           int oneRmReps,
                           float oneRm,
                           String notes, int exerciseNumber,
                           Long mainExerciseHeaderId,
                           List<AssistanceExercise> assistanceExercises) {
        this.id = id;
        this.name = name;
        this.restTime = restTime;
        this.weightIncrement = weightIncrement;
        this.oneRmKg = oneRmKg;
        this.oneRmReps = oneRmReps;
        this.oneRm = oneRm;
        this.notes = notes;
        this.exerciseNumber = exerciseNumber;
        this.mainExerciseHeaderId = mainExerciseHeaderId;
        this.assistanceExercises = assistanceExercises;
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

    public int getExerciseNumber() {
        return exerciseNumber;
    }

    public void setExerciseNumber(int exerciseNumber) {
        this.exerciseNumber = exerciseNumber;
    }

    public Long getMainExerciseHeaderId() {
        return mainExerciseHeaderId;
    }

    public void setMainExerciseHeaderId(Long mainExerciseHeaderId) {
        this.mainExerciseHeaderId = mainExerciseHeaderId;
    }

    public List<AssistanceExercise> getAssistanceExercises() {
        return assistanceExercises;
    }

    public void setAssistanceExercises(List<AssistanceExercise> assistanceExercises) {
        this.assistanceExercises = assistanceExercises;
    }

    @Override
    public String toString() {
        return "MainExerciseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", restTime=" + restTime +
                ", weightIncrement=" + weightIncrement +
                ", oneRmKg=" + oneRmKg +
                ", oneRmReps=" + oneRmReps +
                ", oneRm=" + oneRm +
                ", notes='" + notes + '\'' +
                ", exerciseNumber=" + exerciseNumber +
                '}';
    }
}
