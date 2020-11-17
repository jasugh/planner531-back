package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class MainExercise extends ExerciseBase{
    @Id
    @GeneratedValue
    private Long id;

    private int exerciseNumber;

    @ManyToOne
    @JsonBackReference
    private MainExerciseHeader mainExerciseHeader;

    @ManyToMany
    private List<AssistanceExercise> assistanceExercises = new ArrayList<>();

    public void removeAssistanceExercise(AssistanceExercise assistanceExercise) {
        this.assistanceExercises.remove(assistanceExercise);
    }
}
