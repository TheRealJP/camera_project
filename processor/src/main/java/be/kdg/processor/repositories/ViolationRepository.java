package be.kdg.processor.repositories;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.violations.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ViolationRepository extends JpaRepository<Violation, Long> {

//    //
//    @Query("SELECT c FROM  c WHERE c.dateTime > :timestamp")
//    String findCameraMessageByTimeStamp(@Param("timestamp") LocalDateTime timestamp);

}
