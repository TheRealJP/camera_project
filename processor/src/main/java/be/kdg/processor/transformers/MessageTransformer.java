package be.kdg.processor.transformers;

import be.kdg.processor.models.CameraMessage;
import javafx.scene.Camera;

public interface MessageTransformer {
     CameraMessage transformToCameraMessage(String msg);
}
