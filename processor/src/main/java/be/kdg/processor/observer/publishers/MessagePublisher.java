package be.kdg.processor.observer.publishers;


import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.observer.events.ConsumeEvent;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * publishes only message related events to listeners who are listening to, for example: ConsumeEvent
 */

@Component
public class MessagePublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;


    public void publishMessage(CameraMessage cm, Camera camera, LicensePlate lp) {
        publisher.publishEvent(new ConsumeEvent(this, cm, camera, lp));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
