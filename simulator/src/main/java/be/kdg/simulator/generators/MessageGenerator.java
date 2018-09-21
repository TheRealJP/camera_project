package be.kdg.simulator.generators;

import be.kdg.simulator.messaging.CameraMessage;

public interface MessageGenerator {
    CameraMessage generateCameraMessage();
}
