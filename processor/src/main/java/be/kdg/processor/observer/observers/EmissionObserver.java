package be.kdg.processor.observer.observers;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.service.CameraServiceUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

@Component
public class EmissionObserver implements Observer {

    //    private final Set<EmissionViolation> emissionViolations;
    private final HashMap<LicensePlate, ArrayList<EmissionViolation>> emissionViolations;
    private final CameraServiceUtility cameraServiceUtility;
    private final Logger log = LoggerFactory.getLogger(CameraServiceUtility.class);

    public EmissionObserver(HashMap<LicensePlate, ArrayList<EmissionViolation>> emissionViolations, CameraServiceUtility cameraServiceUtility) {
        this.emissionViolations = emissionViolations;
        this.cameraServiceUtility = cameraServiceUtility;

    }

    /**
     * Checks if one of the cameraMessages has a violation
     * (check on double entries? datetime is unique...still have to check)
     * insert new row into database
     */

    @Override
    public void update(Observable theObservable, Object message) {
        if (message instanceof CameraMessage) {
            CameraMessage m = (CameraMessage) message;

            if (cameraServiceUtility.emissionCheck(m)) {
                //update violations table
                ArrayList<EmissionViolation> licensePlateEmissionViolations = emissionViolations.get(m.getLicensePlate());
                licensePlateEmissionViolations.add(new EmissionViolation());

                emissionViolations.put(m.getLicensePlate(),);

                //new EmissionViolation
                //add to list
            }

        }
    }
}

