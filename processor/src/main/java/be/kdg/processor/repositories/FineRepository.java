package be.kdg.processor.repositories;

import be.kdg.processor.models.fine.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findAllByViolation_Message_DateTimeBetween(LocalDateTime start, LocalDateTime end);
}
