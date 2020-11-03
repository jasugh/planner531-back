package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.MainExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainExerciseRepository extends JpaRepository<MainExercise, Long> {
    MainExercise findFirstByName(String name);

}
