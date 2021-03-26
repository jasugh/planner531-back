package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.SortComparator;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private WorkoutDayExercise workoutDayExercise;
}
