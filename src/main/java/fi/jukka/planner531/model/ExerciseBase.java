package fi.jukka.planner531.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class ExerciseBase {
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
}
