package be.kdg.processor.service.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;

public interface Consumer {
    @RabbitHandler
    void consume(String in);
}
