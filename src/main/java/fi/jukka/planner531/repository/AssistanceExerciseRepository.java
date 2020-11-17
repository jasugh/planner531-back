package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.AssistanceExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistanceExerciseRepository extends JpaRepository<AssistanceExercise, Long> {
    AssistanceExercise findFirstByName(String name);
}
