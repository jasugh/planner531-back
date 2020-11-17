package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class WorkoutDayPlan {
    @Id
    @GeneratedValue
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startingDate;

    @OneToOne(mappedBy = "workoutDayPlan")
    @JsonBackReference
    private Login login;

    @OneToMany(mappedBy = "workoutDayPlan", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @OrderBy("id asc " )
    private List<WorkoutDay> workoutDays = new ArrayList<>();
}
