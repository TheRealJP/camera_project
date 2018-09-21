package be.kdg.simulator.generators;


import be.kdg.simulator.messaging.CameraMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class FileMessageGenerator implements MessageGenerator {

    @Override
    public CameraMessage generateCameraMessage() {
        return new CameraMessage(2, LocalDateTime.now(), "1-XYZ-987");
    }
}
