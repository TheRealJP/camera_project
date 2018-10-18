package be.kdg.processor.repositories;

import be.kdg.processor.models.proxy.LicensePlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicensePlateRepository extends JpaRepository<LicensePlate, Long> {
}
