package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Size(min = 1)
    private String name;
    private String notes;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private final List<Exercise> exercises = new ArrayList<>();
}
