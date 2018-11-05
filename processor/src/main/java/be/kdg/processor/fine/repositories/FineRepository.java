package be.kdg.processor.fine.repositories;

import be.kdg.processor.fine.models.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findAllByFineDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Fine> findAllByLicensePlate(String lp);
}
