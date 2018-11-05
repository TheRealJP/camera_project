package be.kdg.processor.observer.publishers;


import be.kdg.processor.cameramessage.models.Retryable;
import be.kdg.processor.observer.events.RetryableSettingsUpdateEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;


/**
 *  publishes only settings to listeners who are listening for RetryableSettingsUpdateEvent
 */
@Component
public class SettingPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishRetryableEvent(final Retryable retryable) {
        RetryableSettingsUpdateEvent retryableSettingsUpdateEvent = new RetryableSettingsUpdateEvent(this, retryable);
        applicationEventPublisher.publishEvent(retryableSettingsUpdateEvent);
    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
