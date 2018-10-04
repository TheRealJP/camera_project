package be.kdg.processor.transformers;


import be.kdg.processor.models.CameraMessage;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@ConditionalOnProperty(name = "transformer.type", havingValue = "cm")
public class CameraMessageTransformer implements MessageTransformer {

    private static final Logger log = LoggerFactory.getLogger(CameraMessageTransformer.class);

    @Override
    public CameraMessage transformToObject(String msg) {
        return null;
    }

}
