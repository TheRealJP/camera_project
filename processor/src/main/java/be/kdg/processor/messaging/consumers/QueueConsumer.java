package be.kdg.processor.messaging.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "camera-queue")
public class QueueConsumer {

    @RabbitHandler
    public void consume(String in) {
        System.out.println("[x] Message consumed/received: " + in   );
    }

}
