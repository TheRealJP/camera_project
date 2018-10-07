package be.kdg.simulator.transformers;

import be.kdg.simulator.models.CameraMessage;

public interface MessageTransformer {
    Object transformMessage(CameraMessage msg);
}
