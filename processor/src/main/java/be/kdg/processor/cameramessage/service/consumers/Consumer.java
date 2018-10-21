package be.kdg.processor.cameramessage.service.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;

import java.io.IOException;

public interface Consumer {
    @RabbitHandler
    void consume(String in) throws IOException;
}
