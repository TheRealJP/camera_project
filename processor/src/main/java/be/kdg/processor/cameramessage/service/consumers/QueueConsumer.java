package be.kdg.processor.cameramessage.service.consumers;

import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.processor.cameramessage.repositories.CameraMessageRepository;
import be.kdg.processor.violation.observerpattern.events.ConsumeEvent;
import be.kdg.processor.proxy.service.ProxyService;
import be.kdg.processor.cameramessage.service.transformers.MessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    private final CameraMessageRepository cmr;
    private final ProxyService proxyService;

    public QueueConsumer(MessageTransformer transformer, ApplicationEventPublisher applicationEventPublisher, CameraMessageRepository cmr, ProxyService proxyService) {
        this.transformer = transformer;
        this.applicationEventPublisher = applicationEventPublisher;
        this.cmr = cmr;
        this.proxyService = proxyService;
    }

    @RabbitHandler
    public void consume(String in) throws IOException {
        CameraMessage cm = (CameraMessage) transformer.transformMessage(in); //transforms xml to cameramessage object
        Camera camera = proxyService.collectCamera(cm.getCameraId());
        LicensePlate lp = proxyService.collectLicensePlate(cm.getLicensePlate());
        cmr.save(cm);

        applicationEventPublisher.publishEvent(new ConsumeEvent(this, cm, camera, lp));
    }

}
