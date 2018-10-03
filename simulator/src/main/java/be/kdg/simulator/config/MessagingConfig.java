package be.kdg.simulator.config;

import be.kdg.simulator.generators.FileMessageGenerator;
import be.kdg.simulator.models.CameraMessage;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;

@Configuration
public class MessagingConfig {

    @Bean
    public Queue cameraQueue() {
        return new Queue("camera-queue");
    }

    @Bean
    public ArrayList<CameraMessage> cameraMessages() {
        return new ArrayList<>();
    }
}
