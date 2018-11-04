package be.kdg.processor.cameramessage.observerpattern.listeners;

import be.kdg.processor.cameramessage.observerpattern.events.RetryableSettingsUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class RetryableListener implements ApplicationListener<RetryableSettingsUpdateEvent> {
    private static final Logger log = LoggerFactory.getLogger(RetryableListener.class);
    private final RetryTemplate retryTemplate;

    public RetryableListener(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    @Override
    public void onApplicationEvent(RetryableSettingsUpdateEvent event) {
        log.info("Updated retry settings: " + event.getRetryable());

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(event.getRetryable().getRetryableAttempts());

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(event.getRetryable().getRetryableTimeOut());

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
    }
}
