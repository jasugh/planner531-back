package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class WorkoutDay {
    @Id
    @GeneratedValue
    private Long id;
    private int cycle;
    private int week;
    private int dayNumber;
    private boolean completed;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate workoutDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private WorkoutDayPlan workoutDayPlan;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "workoutDay", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<WorkoutDayExercise> workoutDayExercises = new ArrayList<>();
}

