package be.kdg.processor.service.fineservice;

import be.kdg.processor.models.fine.Fine;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.SpeedingViolation;
import be.kdg.processor.models.violations.Violation;
import be.kdg.processor.repositories.FineRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional //dwingt commit af
public class FineService {

    private final FineRepository fineRepository;
    private int fineFactor;

    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    private Fine calculateFine(Violation violation) {
        Fine fine = new Fine();

        if (violation instanceof SpeedingViolation) {
            SpeedingViolation sv = (SpeedingViolation) violation;
            fineFactor = 3;
            double amount = sv.getSpeed() - sv.getFirstCamera().getSegment().getSpeedLimit() * fineFactor;
            fine.setAmount(amount);
            fine.setViolation(sv);

        } else if (violation instanceof EmissionViolation) {
            EmissionViolation ev = (EmissionViolation) violation;
            fineFactor = 150;
            fine.setAmount(fineFactor);
            fine.setViolation(ev);
        }

        return fine;
    }

    public void createAndSaveFine(Violation violation) {
        fineRepository.save(calculateFine(violation));
    }


}
