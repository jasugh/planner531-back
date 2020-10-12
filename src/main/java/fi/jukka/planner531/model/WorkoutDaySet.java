package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class WorkoutDaySet {
    @Id
    @GeneratedValue
    private Long id;
    private float kgs;
    private int reps;

    private boolean finished;
    private String notes;
    private String typeOfSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private WorkoutDay workoutDay;
}
