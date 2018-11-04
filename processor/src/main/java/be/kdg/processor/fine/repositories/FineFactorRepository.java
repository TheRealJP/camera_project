package be.kdg.processor.fine.repositories;

import be.kdg.processor.fine.models.FineFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FineFactorRepository extends JpaRepository<FineFactor, Long> {
    Optional<FineFactor> findFineFactorByViolationType(String violationType);
}
