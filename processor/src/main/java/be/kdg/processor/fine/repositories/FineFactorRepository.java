package be.kdg.processor.fine.repositories;

import be.kdg.processor.fine.models.Fine;
import be.kdg.processor.fine.models.FineFactor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FineFactorRepository extends JpaRepository<FineFactor, Long> {
    Optional<FineFactor> findFineFactorByViolationType(String violationType);
}
