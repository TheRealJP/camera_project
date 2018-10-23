package be.kdg.simulator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    @Bean
    public Queue cameraQueue() {
        return new Queue("camera-queue");
    }
}
