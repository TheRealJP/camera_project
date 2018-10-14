package be.kdg.processor.service.violationcontrol;

import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.Fine;
import be.kdg.processor.models.violations.SpeedingViolation;
import be.kdg.processor.models.violations.Violation;

public class FineService {

    private int fineFactor;

    public FineService(int fineFactor) {
        this.fineFactor = fineFactor;
    }

    public Fine calculateFine(Violation violation) {
        Fine fine = new Fine();
        if (violation instanceof SpeedingViolation) {
            SpeedingViolation sv = (SpeedingViolation) violation;
            fineFactor = 5;
            double amount = sv.getSpeed() - sv.getFirstCamera().getSegment().getSpeedLimit() * fineFactor;
            fine.setAmount(amount);
        } else if (violation instanceof EmissionViolation) {
            fineFactor = 5;
            fine.setAmount(fineFactor);
        }
        return fine;
    }


    //TODO: delegeren naar databank
    public Fine saveFine(Fine fine){

        return fine;
    }


}
