package be.kdg.processor.config;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ConsumerConfig {
    public ArrayList<CameraMessage> cameraMessages() {
        return new ArrayList<>();
    }

}
