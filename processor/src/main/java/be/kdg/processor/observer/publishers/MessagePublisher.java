package be.kdg.processor.observer.publishers;


import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.observer.events.ConsumeEvent;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.processor.proxy.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * publishes only message related events to listeners who are listening to, for example: ConsumeEvent
 */

@Component
public class MessagePublisher implements ApplicationEventPublisherAware {
    @Autowired
    private ProxyService proxyService;
    private ApplicationEventPublisher publisher;


    public void publishMessage(CameraMessage cm) throws IOException {
        Camera camera = proxyService.collectCamera(cm.getCameraId());
        LicensePlate lp = proxyService.collectLicensePlate(cm.getLicensePlate());
        Camera otherCamera = proxyService.collectCamera(camera.getCameraId());

        publisher.publishEvent(new ConsumeEvent(this, cm, camera, otherCamera, lp));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
