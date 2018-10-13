package be.kdg.processor.service.events;

import be.kdg.processor.models.messages.CameraMessage;
import org.springframework.context.ApplicationEvent;

public class ConsumeEvent extends ApplicationEvent {
    private final CameraMessage cameraMessage;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ConsumeEvent(Object source, CameraMessage cameraMessage) {
        super(source);
        this.cameraMessage = cameraMessage;
    }

    @Override
    public String toString() {
        return "ConsumeEvent triggered source=" + source;
    }

    public CameraMessage getCameraMessage() {
        return cameraMessage;
    }
}
