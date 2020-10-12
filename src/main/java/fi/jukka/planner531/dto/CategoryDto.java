package fi.jukka.planner531.dto;

import fi.jukka.planner531.model.Exercise;

import java.util.List;

public class CategoryDto {
    private Long id;
    private String name;
    private String notes;
    private List<Exercise> exercises;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, String notes, List<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.exercises = exercises;
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
