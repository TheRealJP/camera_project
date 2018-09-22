package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component //maakt van RandomMessageGenerator een bean
/*name = verzonnen --> check bij application.properties file*/
@ConditionalOnProperty(name = "generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator {

    @Override
    public CameraMessage generateCameraMessage() {
        return new CameraMessage(1, LocalDateTime.now(), "1-ABC-123");
    }
}
