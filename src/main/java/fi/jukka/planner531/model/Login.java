package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Login {
    @Id
    @GeneratedValue
    private long id;
    private String loginName;

    @JsonIgnore
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate passwordChanged;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Role> roles = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private MainExerciseHeader mainExerciseHeader;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private StartingDetails startingDetails;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private WorkoutDayPlan workoutDayPlan;

    private Date changed;

    private Date created;
}
