package be.kdg.processor.service.violations.observers;


import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.SpeedingViolation;
import be.kdg.processor.models.violations.Violation;
import be.kdg.processor.service.SpeedingViolationService;
import be.kdg.processor.service.ViolationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class SpeedingObserver extends ViolationObserver {

    //    private final Set<EmissionViolation> emissionViolations;
    private final HashMap<String, ArrayList<SpeedingObserver>> speedingViolations;
    private final ViolationService violationService;
    private final Logger log = LoggerFactory.getLogger(SpeedingViolationService.class);

    public SpeedingObserver(HashMap<String, ArrayList<SpeedingObserver>> speedingViolations, ViolationService violationService) {
        this.speedingViolations = speedingViolations;
        this.violationService = violationService;
    }

    @Override
    public void update(Observable theObservable, Object message) {
        try {
            if (message instanceof CameraMessage) {
                CameraMessage m = (CameraMessage) message;
                Violation sv = violationService.checkViolation(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
