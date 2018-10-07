package be.kdg.processor.observer.observers;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.observer.Observer;
import be.kdg.processor.observer.subjects.CameraMessageSubject;
import be.kdg.processor.service.CameraServiceUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EmissionObserver implements Observer {

    private final ArrayList<EmissionViolation> emissionViolations;
    private final CameraServiceUtility cameraServiceUtility;
    private final Logger log = LoggerFactory.getLogger(CameraServiceUtility.class);

    public EmissionObserver(ArrayList<EmissionViolation> emissionViolations, CameraServiceUtility cameraServiceUtility) {
        this.emissionViolations = emissionViolations;
        this.cameraServiceUtility = cameraServiceUtility;
    }

    /**
     * Checks if one of the cameraMessages has a violation
     *
     */
    @Override
    public void update(Object object) {
        if (object instanceof CameraMessageSubject) {
            ArrayList<CameraMessage> messages = ((CameraMessageSubject) object).getMessages();
            for (CameraMessage m : messages) {
                if (cameraServiceUtility.emissionCheck(m)){
                    //check on double entries
                    //new EmissionViolation
                    //add to list
                }
            }

        }
    }
}

