package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findFirstByName(String name);
}

