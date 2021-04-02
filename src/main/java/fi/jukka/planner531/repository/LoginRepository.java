package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.Login;
import fi.jukka.planner531.model.Role;
import fi.jukka.planner531.model.WorkoutDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.util.List;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findFirstByLoginName(String loginName);
    Login findByLoginName(String loginName);
}
