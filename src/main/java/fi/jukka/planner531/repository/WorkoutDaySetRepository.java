package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.WorkoutDaySet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WorkoutDaySetRepository extends JpaRepository<WorkoutDaySet, Long> {
 List<WorkoutDaySet> findAllByWorkoutDayExerciseId(Long id);
}
