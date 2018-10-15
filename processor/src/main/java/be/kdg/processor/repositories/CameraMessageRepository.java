package be.kdg.processor.repositories;

import be.kdg.processor.models.messages.CameraMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CameraMessageRepository extends JpaRepository<CameraMessage, Long> {

//    //
//    @Query("SELECT c FROM  c WHERE c.dateTime > :timestamp")
//    String findCameraMessageByTimeStamp(@Param("timestamp") LocalDateTime timestamp);

}
