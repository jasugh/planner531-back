package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findFirstByName(String name);
}
