package be.kdg.processor.repositories;

import be.kdg.processor.models.violations.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine,Long> {

}
