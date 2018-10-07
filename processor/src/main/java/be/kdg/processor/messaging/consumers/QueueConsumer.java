package be.kdg.processor.messaging.consumers;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.observer.Subject;
import be.kdg.processor.service.CameraServiceUtility;
import be.kdg.processor.transformers.MessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "camera-queue")
public class QueueConsumer {

    private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);
    private final CameraServiceUtility cameraServiceUtility;
    private final MessageTransformer transformer;
    private final Subject subject;

    public QueueConsumer(CameraServiceUtility cameraServiceUtility, MessageTransformer transformer, Subject subject) {
        this.cameraServiceUtility = cameraServiceUtility;
        this.transformer = transformer;
        this.subject = subject;
    }

    //    https://docs.spring.io/spring-amqp/reference/htmlsingle/#message-builder
    @RabbitHandler
    public void consume(String in) {
        CameraMessage cm = (CameraMessage) transformer.transformMessage(in); //transforms xml to cameramessage object
        subject.addMessage(cm);
        log.info("Message received: " + cm);
    }


}
