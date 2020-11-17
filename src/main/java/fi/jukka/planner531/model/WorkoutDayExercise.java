package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class WorkoutDayExercise {
    @Id
    @GeneratedValue
    private Long id;
    private Long exerciseBaseId;
    private String name;
    private int sequenceNumber;

    @ManyToOne
    @JsonBackReference
    private WorkoutDay workoutDay;

    @OneToMany(mappedBy = "workoutDayExercise", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<WorkoutDaySet> workoutDaySets = new ArrayList<>();
}
