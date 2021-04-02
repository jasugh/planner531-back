package fi.jukka.planner531.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor
@Entity
public class StartingDetails {
    @Id
    @GeneratedValue
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startingDate;
    private float pressKg;
    private int pressReps;
    private float press1rm;
    private float deadLiftKg;
    private int deadLiftReps;
    private float deadLift1rm;
    private float benchPressKg;
    private int benchPressReps;
    private float benchPress1rm;
    private float squatKg;
    private int squatReps;
    private float squat1rm;
    private float weightRounding;
    private float pressIncrement;
    private float deadLiftIncrement;
    private float benchIncrement;
    private float squatIncrement;
    private float trainingMax;
    private float numberOfCycles;
    @ElementCollection()
    private List<Integer> w1percentages = new ArrayList<Integer>(3);
    @ElementCollection
    private List<Integer> w2percentages = new ArrayList<Integer>(3);
    @ElementCollection
    private List<Integer> w3percentages = new ArrayList<Integer>(3);
    @ElementCollection
    private List<Integer> w4percentages = new ArrayList<Integer>(3);

    @OneToOne(mappedBy = "startingDetails")
    @JsonBackReference
    private Login login;

    private Date created;
}
