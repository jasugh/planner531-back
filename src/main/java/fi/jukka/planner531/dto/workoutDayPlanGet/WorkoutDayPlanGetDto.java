package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDayPlanGetDto {
    private Long id;
    private LocalDate startingDate;
    private List<CycleDto> cycleDtos = new ArrayList<>();

    public WorkoutDayPlanGetDto() {
    }

    public WorkoutDayPlanGetDto(Long id, LocalDate startingDate, List<CycleDto> cycleDtos) {
        this.id = id;
        this.startingDate = startingDate;
        this.cycleDtos = cycleDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public List<CycleDto> getCycleDtos() {
        return cycleDtos;
    }

    public void setCycleDto(List<CycleDto> cycleDtos) {
        this.cycleDtos = cycleDtos;
    }
}
