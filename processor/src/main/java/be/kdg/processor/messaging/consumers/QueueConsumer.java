package be.kdg.processor.messaging.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "camera-queue")
public class QueueConsumer {

    private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

    @RabbitHandler
    public void consume(String in) {

        log.info("[x] Message consumed/received: " + in);
    }

}
