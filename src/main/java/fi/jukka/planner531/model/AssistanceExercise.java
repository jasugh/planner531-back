package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AssistanceExercise extends ExerciseBase {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Category category;
}
