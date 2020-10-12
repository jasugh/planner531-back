package fi.jukka.planner531.repository;

import fi.jukka.planner531.model.StartingDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StartingDetailsRepository extends JpaRepository<StartingDetails, Long> {
    StartingDetails findByLoginId(Long id);
}
