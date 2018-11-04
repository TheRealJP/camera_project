package be.kdg.processor.cameramessage.config;

import be.kdg.processor.cameramessage.models.Retryable;
import be.kdg.processor.cameramessage.service.retryable.RetryableService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Optional;

@Configuration
@EnableRetry
public class RetryableConfig {
    private final RetryableService retryableService;

    public RetryableConfig(RetryableService retryableService) {
        this.retryableService = retryableService;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        Optional<Retryable> retryable = Optional.ofNullable(retryableService.getRetryable());
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(retryable.map(Retryable::getRetryableTimeOut).orElse(2000));

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(retryable.map(Retryable::getRetryableAttempts).orElse(2));

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
