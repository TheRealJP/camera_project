package be.kdg.processor.violation.repository;

import be.kdg.processor.violation.models.TimeFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeFrameRepository extends JpaRepository<TimeFrame,Long> {

}
