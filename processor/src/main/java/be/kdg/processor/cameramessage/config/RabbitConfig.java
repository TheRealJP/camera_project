package be.kdg.processor.cameramessage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
public class RabbitConfig {
    public static final String EXCHANGE_NAME = "cameraExchange";
    public static final String MESSAGE_QUEUE = "camera-queue";
    public static final String ROUTING_ERROR_KEY = "errors.key";
    public static final String ERROR_QUEUE = "error-queue";
    public static final String ROUTING_MESSAGE_KEY = "messages.key";

    @Bean
    public Queue messageQueue() {
        return new Queue(MESSAGE_QUEUE);
    }

    @Bean
    public Queue errorQueue() {
        return new Queue(ERROR_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding declareBindingMessage() {
        return BindingBuilder.bind(messageQueue()).to(exchange()).with(ROUTING_MESSAGE_KEY);
    }

    @Bean
    public Binding declareBindingError() {
        return BindingBuilder.bind(errorQueue()).to(exchange()).with(ROUTING_ERROR_KEY);
    }


}
