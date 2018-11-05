package be.kdg.processor.observer.listeners;

import be.kdg.processor.observer.events.RetryableSettingsUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

/**
 * listens to updates in the retryable table and applies them to the retryTemplate
 */

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
