package be.kdg.processor.service;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.violations.Violation;
import be.kdg.processor.repositories.CameraMessageRepository;
import be.kdg.processor.repositories.ViolationRepository;
import be.kdg.processor.service.fineservice.FineService;
import be.kdg.processor.service.violationservice.ViolationService;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class ViolationHandler {

    private final Logger log = LoggerFactory.getLogger(ViolationHandler.class);
    private final ViolationService violationService;
    private final FineService fineService;
    private final CameraMessageRepository cmr; //TODO vervangen met service
    private final ViolationRepository violationRepository;

    public ViolationHandler(ViolationService violationService, FineService fineService, CameraMessageRepository cmr, ViolationRepository violationRepository) {
        this.violationService = violationService;
        this.fineService = fineService;
        this.cmr = cmr;
        this.violationRepository = violationRepository;
    }

    public void handleViolations() {
        try {
            //TODO: elke minuut checken op overtredingen? of real time?
            List<CameraMessage> cameraMessages = cmr.findAll();

            if (cameraMessages.size() > 0) {
                Violation violation = violationService.checkViolation(cameraMessages);
                log.info("violation: " + violation.toString());

                //check if violation already exists
                Optional<Violation> existingViolation = Optional.ofNullable(violationRepository.getOne(violation.getId()));
                if (!existingViolation.isPresent()) {
                    log.info("making a fine");
                    fineService.createAndSaveFine(violation);
                }
            }

        } catch (IOException | LicensePlateNotFoundException | CameraNotFoundException e) {
            log.error(e.getMessage());
        }
    }

}
