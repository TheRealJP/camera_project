package be.kdg.processor.violation.observerpattern.listeners;

import be.kdg.processor.violation.models.EmissionViolation;
import be.kdg.processor.fine.service.FineService;
import be.kdg.processor.violation.observerpattern.events.ConsumeEvent;
import be.kdg.processor.violation.service.EmissionViolationService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class EmissionViolationListener implements ApplicationListener<ConsumeEvent> {
    private final FineService fineService;
    private final EmissionViolationService emissionViolationService;


    public EmissionViolationListener(FineService fineService, EmissionViolationService emissionViolationService) {
        this.fineService = fineService;
        this.emissionViolationService = emissionViolationService;
    }

    @Override
    public void onApplicationEvent(ConsumeEvent event) {
        try {
            EmissionViolation emissionViolation = emissionViolationService.checkViolation(event);
            if (emissionViolation != null)
                fineService.createAndSaveFine(emissionViolation);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
