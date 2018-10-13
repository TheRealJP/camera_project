package be.kdg.processor.service.consumers;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.service.events.ConsumeEvent;
import be.kdg.processor.service.transformers.MessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

//https://www.youtube.com/watch?v=ohL2HIBK1pg


/**
 * transforms incoming xml to cameramessage object
 * publishes message to listeners
 */

@Component
@RabbitListener(queues = "camera-queue")
public class QueueConsumer implements Consumer {

    private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);
    private final MessageTransformer transformer;
    private final ApplicationEventPublisher applicationEventPublisher;

    public QueueConsumer(MessageTransformer transformer, ApplicationEventPublisher applicationEventPublisher) {
        this.transformer = transformer;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @RabbitHandler
    public void consume(String in) {
        CameraMessage cm = (CameraMessage) transformer.transformMessage(in); //transforms xml to cameramessage object
        applicationEventPublisher.publishEvent(new ConsumeEvent(this, cm));
        log.info("Message received: " + cm);
    }

}
