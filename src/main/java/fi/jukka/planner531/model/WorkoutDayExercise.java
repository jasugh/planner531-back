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

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private WorkoutDay workoutDay;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "workoutDayExercise", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<WorkoutDaySet> workoutDaySets = new ArrayList<>();
}
