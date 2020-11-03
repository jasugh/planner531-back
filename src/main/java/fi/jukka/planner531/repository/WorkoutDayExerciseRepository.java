package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.WorkoutDayExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutDayExerciseRepository extends JpaRepository<WorkoutDayExercise, Long> {
}
