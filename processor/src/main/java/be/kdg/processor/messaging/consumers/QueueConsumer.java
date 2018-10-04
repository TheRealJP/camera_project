package be.kdg.processor.messaging.consumers;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.transformers.MessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class QueueConsumer {

    private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);
    private final MessageTransformer messageTransformer;
    private final ArrayList<CameraMessage> cameraMessages; //doesnt share relation with arraylist in the simulator module
    private final RabbitTemplate template;
    private final ArrayList<String> licensePlates;


    public QueueConsumer(MessageTransformer messageTransformer, ArrayList<CameraMessage> cameraMessages, RabbitTemplate template, ArrayList<String> licensePlates) {
        this.messageTransformer = messageTransformer;
        this.cameraMessages = cameraMessages;
        this.template = template;
        this.licensePlates = licensePlates;
    }

    //    https://docs.spring.io/spring-amqp/reference/htmlsingle/#message-builder
    @RabbitListener(queues = "camera-queue")
    public void consume(String in) {
        licensePlates.add(messageTransformer.transformToObject(in).toString());
        log.info("[x] Message consumed/received: " + in);
    }

}
