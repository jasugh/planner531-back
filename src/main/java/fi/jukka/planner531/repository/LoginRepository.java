package fi.jukka.planner531.repository;
import fi.jukka.planner531.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoginRepository extends JpaRepository<Login, Long> {
    List<Login> findAllByOrderById();
    Login findFirstByLoginName(String loginName);
    Login findByLoginName(String loginName);
}
