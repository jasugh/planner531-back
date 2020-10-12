package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findFirstByLoginName(String loginName);
    Login findByLoginName(String loginName);
}
