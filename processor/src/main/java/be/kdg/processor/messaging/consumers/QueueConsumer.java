package be.kdg.processor.messaging.consumers;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.transformers.MessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//@Component
@Service
public class QueueConsumer {

    private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);
    private final MessageTransformer messageTransformer;
    private final ArrayList<CameraMessage> cameraMessages; //doesnt share relation with arraylist in the simulator module

    public QueueConsumer(MessageTransformer messageTransformer, ArrayList<CameraMessage> cameraMessages) {
        this.messageTransformer = messageTransformer;
        this.cameraMessages = cameraMessages;
    }

    @RabbitListener(queues = "camera-queue")
    public void consume(String in) {
        cameraMessages.add(messageTransformer.transformToCameraMessage(in));
        log.info("[x] Message consumed/received: " + in);
    }

}
