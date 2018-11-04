package be.kdg.processor.cameramessage.observerpattern.publishers;


import be.kdg.processor.cameramessage.models.Retryable;
import be.kdg.processor.cameramessage.observerpattern.events.RetryableSettingsUpdateEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class RetryablePublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final Retryable retryable) {
        System.out.println("Publishing custom event. ");
        RetryableSettingsUpdateEvent customSpringEvent = new RetryableSettingsUpdateEvent(this, retryable);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
