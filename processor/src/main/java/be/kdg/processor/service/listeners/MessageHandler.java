package be.kdg.processor.service.listeners;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.repositories.CameraMessageRepository;
import be.kdg.processor.service.ViolationHandler;
import be.kdg.processor.service.events.ConsumeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class MessageHandler /*implements ApplicationListener<ConsumeEvent> */{
    private final Logger log = LoggerFactory.getLogger(MessageHandler.class);
    private final CameraMessageRepository cmr;
    private final ViolationHandler violationHandler;

    public MessageHandler(CameraMessageRepository cmr, ViolationHandler violationHandler) {
        this.cmr = cmr;
        this.violationHandler = violationHandler;
    }

    /**
     * (check on double entries? datetime is unique...still have to check)
     * insert new row into database
     */

//    public void onApplicationEvent(ConsumeEvent event) {
//        CameraMessage cm = event.getCameraMessage();
//        cmr.save(cm);
//        log.info("saved new message: " + cm.toString());
//    }
}

