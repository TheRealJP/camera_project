package be.kdg.simulator.generators;

import be.kdg.simulator.SimulatorApplication;
import be.kdg.simulator.messaging.CameraMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component //maakt van RandomMessageGenerator een bean

/*verzonnen --> check bij application.properties file*/
@ConditionalOnProperty(name = "generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator {

    @Override
    public CameraMessage generateCameraMessage() {
        return new CameraMessage(1, LocalDateTime.now(), "1-ABC-123");
    }
}
