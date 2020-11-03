package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class MainExerciseHeader {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "mainExerciseHeader")
    @JsonManagedReference(value = "main-exercises")
    @Size(max = 4)
    @OrderBy("exerciseNumber asc ")
    private List<MainExercise> mainExercises = new ArrayList<>();

    @OneToOne(mappedBy = "mainExerciseHeader")
    @JsonBackReference(value = "login-mainExerciseHeader")
    private Login login;
}
