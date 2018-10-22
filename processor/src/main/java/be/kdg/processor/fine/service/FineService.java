package be.kdg.processor.fine.service;

import be.kdg.processor.fine.exceptions.FineException;
import be.kdg.processor.fine.models.Fine;
import be.kdg.processor.fine.models.FineFactor;
import be.kdg.processor.fine.repositories.FineFactorRepository;
import be.kdg.processor.fine.repositories.FineRepository;
import be.kdg.processor.violation.models.EmissionViolation;
import be.kdg.processor.violation.models.SpeedingViolation;
import be.kdg.processor.violation.models.Violation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional //dwingt commit af
public class FineService {
    private final FineRepository fineRepository;
    private final FineFactorRepository ffRepo;

    public FineService(FineRepository fineRepository, FineFactorRepository ffRepo) {
        this.fineRepository = fineRepository;
        this.ffRepo = ffRepo;
    }

    public Fine createAndSaveFine(Violation violation) {
        return fineRepository.save(calculateFine(violation));
    }

    public Fine save(Fine fine) {
        return fineRepository.save(fine);
    }

    public List<Fine> getFilteredFines(LocalDateTime startTime, LocalDateTime endTime) throws FineException {
        List<Fine> optionalFineList = fineRepository.findAllByFineDateTimeBetween(startTime, endTime);
        if (optionalFineList.size() > 0)
            return optionalFineList;
        throw new FineException("Fines list not found");
    }

    public Fine get(Long id) throws FineException {
        Optional<Fine> optionalFine = fineRepository.findById(id);
        if (optionalFine.isPresent()) {
            return optionalFine.get();
        }
        throw new FineException("Fine not found");
    }

    public List<Fine> getAll() {
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

            Optional<FineFactor> fineFactor = ffRepo.findFineFactorByViolationType("Speed");
            if (!fineFactor.isPresent()) {
                fineFactor = Optional.ofNullable(ffRepo.save(new FineFactor(3, "Speed")));
            }
            double money = sv.getSpeed() - sv.getCam().getSegment().getSpeedLimit() * fineFactor.get().getFactor();
            fine.setAmount(money);

            fine.setFineDateTime(violation.getMessage().getDateTime());
            fine.setSpeed(sv.getSpeed());
            fine.setSpeedLimit(sv.getCam().getSegment().getSpeedLimit());
            fine.setLicensePlate(sv.getMessage().getLicensePlate());
        } else if (violation instanceof EmissionViolation) {
            EmissionViolation ev = (EmissionViolation) violation;
            fine.setFineDateTime(violation.getMessage().getDateTime());
            fine.setCarEmission(ev.getLicensePlateEuroNorm());
            fine.setCameraEmission(ev.getCameraEuroNorm());

            Optional<FineFactor> fineFactor = ffRepo.findFineFactorByViolationType("Emission");
            if (fineFactor.isPresent()) fine.setAmount(fineFactor.get().getFactor());
            else {
                FineFactor ffEmission = ffRepo.save(new FineFactor(150, "Emission"));
                fine.setAmount(ffEmission.getFactor());
            }
        }

        return fine;
    }
}
