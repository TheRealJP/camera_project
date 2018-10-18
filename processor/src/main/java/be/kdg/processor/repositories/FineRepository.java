package be.kdg.processor.repositories;

import be.kdg.processor.models.fine.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    // message do 14:55 - vrijdag 13:55
//    @Query("SELECT c.dateTime FROM Violations c where CameraMessage.dateTime - CURRENT_TIMESTAMP > :timestamp")
//    String findViolationsByTimeFrame(@Param("timestamp") LocalDateTime timestamp);

//    List<Fine> findAllByViolationCameraMessageTimestampBetween(LocalDateTime start, LocalDateTime end);

}
