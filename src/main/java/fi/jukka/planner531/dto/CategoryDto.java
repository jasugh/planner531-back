package fi.jukka.planner531.dto;

import fi.jukka.planner531.model.AssistanceExercise;

import java.util.List;

public class CategoryDto {
    private Long id;
    private String name;
    private String notes;
    private List<AssistanceExercise> assistanceExercises;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, String notes, List<AssistanceExercise> assistanceExercises) {
        this.id = id;
        this.name = name;
        this.notes = notes;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<AssistanceExercise> getExercises() {
        return assistanceExercises;
    }

    public void setExercises(List<AssistanceExercise> assistanceExercises) {
        this.assistanceExercises = assistanceExercises;
    }
}
