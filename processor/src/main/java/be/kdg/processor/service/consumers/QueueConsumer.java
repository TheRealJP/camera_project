package be.kdg.processor.service.consumers;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.service.transformers.MessageTransformer;
import be.kdg.processor.service.violations.observers.ViolationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Observable;

//https://www.youtube.com/watch?v=ohL2HIBK1pg


@Component
@RabbitListener(queues = "camera-queue")
public class QueueConsumer extends Observable implements Consumer {

    private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);
    private final MessageTransformer transformer;
    private final ViolationObserver violationObserver;

    public QueueConsumer(MessageTransformer transformer, ViolationObserver violationObserver) {
        this.transformer = transformer;
        this.violationObserver = violationObserver;
        this.addObserver(violationObserver);
    }

    @RabbitHandler
    public void consume(String in) {
        CameraMessage cm = (CameraMessage) transformer.transformMessage(in); //transforms xml to cameramessage object
        violationObserver.update(this, cm);
        log.info("Message received: " + cm);
    }
}
