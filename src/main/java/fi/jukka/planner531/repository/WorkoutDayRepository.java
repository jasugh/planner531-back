package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.WorkoutDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutDayRepository extends JpaRepository<WorkoutDay, Long> {
    List<WorkoutDay> findAllByWorkoutDayPlanId(Long id);
    void deleteAllByWorkoutDayPlanId(Long id);
}
