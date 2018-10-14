package be.kdg.processor.service.listeners;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.service.events.ConsumeEvent;
import be.kdg.processor.service.violationcontrol.ViolationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageHandler implements ApplicationListener<ConsumeEvent> {
    private ViolationService violationService;
    private final Logger log = LoggerFactory.getLogger(MessageHandler.class);
    private final MessageBuffer messageBuffer;

    public MessageHandler(ViolationService violationService, MessageBuffer messageBuffer) {
        this.violationService = violationService;
        this.messageBuffer = messageBuffer;
    }

    /**
     * Checks if one of the cameraMessages has a violation
     * (check on double entries? datetime is unique...still have to check)
     * insert new row into database
     */

    @Override
    public void onApplicationEvent(ConsumeEvent event) {
        try {
            CameraMessage cm = event.getCameraMessage();
            messageBuffer.addMessage(cm);
            violationService.checkViolation();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

