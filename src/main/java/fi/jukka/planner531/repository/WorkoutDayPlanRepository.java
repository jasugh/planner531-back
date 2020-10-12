package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.WorkoutDayPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutDayPlanRepository extends JpaRepository<WorkoutDayPlan, Long> {

}
