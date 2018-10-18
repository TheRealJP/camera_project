package be.kdg.processor.service.fineservice;

import be.kdg.processor.exceptions.FineException;
import be.kdg.processor.models.fine.Fine;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.SpeedingViolation;
import be.kdg.processor.models.violations.Violation;
import be.kdg.processor.repositories.FineRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional //dwingt commit af
public class FineService {

    private final FineRepository fineRepository;

    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public void createAndSaveFine(Violation violation) {
        fineRepository.save(calculateFine(violation));
    }
//
//    public List<Fine> getFilteredFines(LocalDateTime startTime, LocalDateTime endTime) throws FineException{
//        List<Fine> optionalFineList = fineRepository.findAllByViolationCameraMessageTimestampBetween(startTime, endTime);
//
//        if (optionalFineList.size() > 0)
//            return optionalFineList;
//
//        throw new FineException("Fines list not found");
//    }

    public Fine save(Fine fine) {
        return fineRepository.save(fine);
    }

    public Fine get(Long id) throws FineException {
        Optional<Fine> optionalFine = fineRepository.findById(id);
        if (optionalFine.isPresent()) {
            return optionalFine.get();
        }
        throw new FineException("Fine not found");
    }

    public List<Fine> getAll() throws FineException {
        return fineRepository.findAll();
    }

    public Fine setApproveFine(Long id, boolean approved) throws FineException {
        Fine fine = get(id);
        fine.setApproved(approved);
        return fine;
    }


    private Fine calculateFine(Violation violation) {
        Fine fine = new Fine();

        if (violation instanceof SpeedingViolation) {
            SpeedingViolation sv = (SpeedingViolation) violation;
            fine.setFinefactor(3);
            double amount = sv.getSpeed() - sv.getCam().getSegment().getSpeedLimit() * fine.getFinefactor();
            fine.setAmount(amount);
            fine.setViolation(sv);
            fine.setSpeed(sv.getSpeed());
            fine.setSpeedLimit(sv.getCam().getSegment().getSpeedLimit());

        } else if (violation instanceof EmissionViolation) {
            EmissionViolation ev = (EmissionViolation) violation;
            fine.setFinefactor(150);
            fine.setAmount(fine.getFinefactor());
            fine.setViolation(ev);
        }

        return fine;
    }
}
