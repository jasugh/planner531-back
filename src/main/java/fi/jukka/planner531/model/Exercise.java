package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Exercise {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int restTime;
    private float weightIncrement;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Category category;
}
