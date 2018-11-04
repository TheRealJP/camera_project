package be.kdg.processor.cameramessage.repositories;

import be.kdg.processor.cameramessage.models.Retryable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetryableRepository extends JpaRepository<Retryable, Long> {

}
