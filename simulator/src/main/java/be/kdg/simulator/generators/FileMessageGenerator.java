package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileMessageGenerator implements MessageGenerator {

    @Override
    public CameraMessage generateCameraMessage() {
        return new CameraMessage(2, LocalDateTime.now(), "1-XYZ-987");
    }

    //TODO: read txt and convert to message objects

}
