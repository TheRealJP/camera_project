package be.kdg.processor.fine.service;

import be.kdg.processor.fine.exceptions.FineException;
import be.kdg.processor.fine.models.EmissionFine;
import be.kdg.processor.fine.models.Fine;
import be.kdg.processor.fine.models.FineFactor;
import be.kdg.processor.fine.models.SpeedFine;
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
@Transactional
public class FineService {
    private final FineRepository fineRepository;
    private final FineFactorRepository ffRepo;

    public FineService(FineRepository fineRepository, FineFactorRepository ffRepo) {
        this.fineRepository = fineRepository;
        this.ffRepo = ffRepo;
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

    public SpeedFine createSpeedFine(Violation violation) {
        SpeedingViolation sv = (SpeedingViolation) violation;

        Optional<FineFactor> fineFactor = ffRepo.findFineFactorByViolationType("Speed");
        if (!fineFactor.isPresent()) fineFactor = Optional.ofNullable(ffRepo.save(new FineFactor(3, "Speed")));
        int speedLimit = sv.getCam().getSegment().getSpeedLimit();
        String licensePlate = sv.getMessage().getLicensePlate();
        LocalDateTime dateTime = violation.getMessage().getDateTime();
        double money = sv.getSpeed() - speedLimit * fineFactor.get().getFactor();
        return new SpeedFine(money, licensePlate, dateTime, speedLimit, sv.getSpeed());
    }

    public EmissionFine createEmissionFine(Violation violation) {
        EmissionViolation ev = (EmissionViolation) violation;
        LocalDateTime dateTime = violation.getMessage().getDateTime();
        int lpNorm = ev.getLicensePlateEuroNorm();
        int camNorm = ev.getCameraEuroNorm();
        String lp = ev.getMessage().getLicensePlate();

        Optional<FineFactor> fineFactor = ffRepo.findFineFactorByViolationType("Emission");
        int factor;
        factor = fineFactor.map(FineFactor::getFactor).orElseGet(() -> ffRepo.save(new FineFactor(150, "Emission")).getFactor());
        return new EmissionFine(factor, lp, dateTime, camNorm, lpNorm);
    }
}
