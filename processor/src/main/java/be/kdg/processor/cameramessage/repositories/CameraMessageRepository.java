package be.kdg.processor.cameramessage.repositories;

import be.kdg.processor.cameramessage.models.CameraMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CameraMessageRepository extends JpaRepository<CameraMessage, Long> {
        List<CameraMessage> findAllByDateTimeIsAfter(LocalDateTime timePoint);
}
