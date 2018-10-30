package be.kdg.processor.violation.observerpattern.listeners;

import be.kdg.processor.fine.models.EmissionFine;
import be.kdg.processor.fine.models.Fine;
import be.kdg.processor.fine.service.FineService;
import be.kdg.processor.violation.models.EmissionViolation;
import be.kdg.processor.violation.observerpattern.events.ConsumeEvent;
import be.kdg.processor.violation.service.EmissionViolationService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


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
        EmissionViolation emissionViolation = emissionViolationService.checkViolation(event);
        if (emissionViolation != null){
            Fine fine = fineService.createEmissionFine(emissionViolation);
            fineService.save(fine);
        }
    }
}
