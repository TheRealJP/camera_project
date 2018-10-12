package be.kdg.processor.service.violations.observers;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.Violation;
import be.kdg.processor.service.EmissionViolationService;
import be.kdg.processor.service.SpeedingViolationService;
import be.kdg.processor.service.ViolationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

@Component
public class EmissionObserver extends ViolationObserver {
    // private final Set<EmissionViolation> emissionViolations;
    private final HashMap<String, ArrayList<EmissionViolation>> emissionViolations;
    private final ViolationService violationService;
    private final Logger log = LoggerFactory.getLogger(SpeedingViolationService.class);

    public EmissionObserver(HashMap<String, ArrayList<EmissionViolation>> emissionViolations, ViolationService violationService) {
        this.emissionViolations = emissionViolations;
        this.violationService = violationService;
    }

    /**
     * Checks if one of the cameraMessages has a violation
     * (check on double entries? datetime is unique...still have to check)
     * insert new row into database
     */

    @Override
    public void update(Observable theObservable, Object message) {
        try {
            if (message instanceof CameraMessage) {
                CameraMessage m = (CameraMessage) message;

                Violation ev = violationService.checkViolation(m);

                if (ev != null) {
                    //TODO: later this wel insert a new violation in its table
                    ArrayList<EmissionViolation> licensePlateEmissionViolations = emissionViolations.get(m.getLicensePlate());
//                    if (licensePlateEmissionViolations != null)
//                        licensePlateEmissionViolations.add((EmissionViolation) ev);
//                    else {
//                        licensePlateEmissionViolations = new ArrayList<>();
//                        licensePlateEmissionViolations.add((EmissionViolation) ev);
//                    }
                    emissionViolations.put(m.getLicensePlate(), licensePlateEmissionViolations);
                    log.info(String.format("%d emissionviolations for %s", emissionViolations.get(m.getLicensePlate()).size(), m.getLicensePlate()));
                }
            }
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
        }
    }
}

