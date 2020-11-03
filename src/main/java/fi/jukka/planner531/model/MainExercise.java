package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class MainExercise {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int restTime;
    private float weightIncrement;
    private float oneRmKg;
    private int oneRmReps;
    private float oneRm;
    private String notes;
    private int exerciseNumber;

    @ManyToOne
    @JsonBackReference(value = "main-exercises")
    private MainExerciseHeader mainExerciseHeader;

//    @ManyToMany
//    private List<Exercise> assistanceExercises = new ArrayList<>();
}
