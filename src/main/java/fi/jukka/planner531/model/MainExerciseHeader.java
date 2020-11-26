package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.core.annotation.Order;

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

    @OneToOne(mappedBy = "mainExerciseHeader")
    @JsonBackReference
    private Login login;

    @OneToMany(mappedBy = "mainExerciseHeader")
    @JsonManagedReference(value = "header-exercise")
    @OrderBy("exerciseNumber asc ")
    @Size(max = 4)
    private List<MainExercise> mainExercises = new ArrayList<>();
}
