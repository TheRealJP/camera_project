package be.kdg.processor.repositories;

import be.kdg.processor.models.proxy.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<Camera,Long> {
}
