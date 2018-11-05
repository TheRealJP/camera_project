package be.kdg.processor.observer.listeners;

import be.kdg.processor.fine.models.Fine;
import be.kdg.processor.fine.service.FineService;
import be.kdg.processor.violation.models.SpeedingViolation;
import be.kdg.processor.observer.events.ConsumeEvent;
import be.kdg.processor.violation.service.SpeedViolationService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * listens to incoming messages in the consumer and makes fines if the message has any violations
 */

@Component
public class SpeedViolationListener implements ApplicationListener<ConsumeEvent> {
    private final FineService fineService;
    private final SpeedViolationService speedViolationService;


    public SpeedViolationListener(FineService fineService, SpeedViolationService speedViolationService) {
        this.fineService = fineService;
        this.speedViolationService = speedViolationService;
    }

    @Override
    public void onApplicationEvent(ConsumeEvent event) {
        try {
            SpeedingViolation speedingViolation = speedViolationService.checkViolation(event);
            if (speedingViolation != null) {
                Fine fine = fineService.createSpeedFine(speedingViolation);
                fineService.save(fine);
            }

        } catch (IOException | ArithmeticException e) {
            e.printStackTrace();
        }
    }
}
