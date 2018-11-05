package be.kdg.processor.observer.events;

import be.kdg.processor.cameramessage.models.Retryable;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;


public class RetryableSettingsUpdateEvent extends ApplicationEvent {
    private final Retryable retryable;

    public RetryableSettingsUpdateEvent(Object source, Retryable retryable) {
        super(source);
        this.retryable = retryable;
        System.out.println(this.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetryableSettingsUpdateEvent that = (RetryableSettingsUpdateEvent) o;
        return Objects.equals(retryable, that.retryable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(retryable);
    }

    public String toString() {
        return "RetryableSettingsUpdateEvent triggered source=" + source;
    }

    public Retryable getRetryable() {
        return retryable;
    }


}
