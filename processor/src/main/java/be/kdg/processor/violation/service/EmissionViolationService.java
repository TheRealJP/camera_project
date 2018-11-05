package be.kdg.processor.violation.service;

import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.fine.models.Fine;
import be.kdg.processor.fine.repositories.FineRepository;
import be.kdg.processor.observer.events.ConsumeEvent;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.processor.violation.models.EmissionViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EmissionViolationService implements ViolationService {
    private final Logger log = LoggerFactory.getLogger(EmissionViolationService.class);
    private final FineRepository fineRepository;
    private long timeFrame;

    public EmissionViolationService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
        this.timeFrame = 86400000;
    }


    /**
     * latest fine timestamp + timeframe = endpoint of timeframe,
     * after this point we'll create a new fine if the car enters a low emission zone
     */
    @Override
    public EmissionViolation checkViolation(ConsumeEvent event) {
        Camera cam = event.getCamera();
        LicensePlate lp = event.getLp();
        CameraMessage cm = event.getCameraMessage();

        List<Fine> oldFines = fineRepository.findAllByLicensePlate(cm.getLicensePlate());
        Fine latestFine;
        LocalDateTime offsetLastFine = LocalDateTime.now();

        //offset = latest fine's datetime + timeframe emissionviolation
        if (oldFines.size() != 0) {
            latestFine = oldFines.get(oldFines.size() - 1);
            offsetLastFine = latestFine.getFineDateTime().plus(timeFrame, ChronoUnit.MILLIS);
        }

        //if the licenseplate doesn't have a fine already or the last fine is out of the timeframe bounds create a new fine
        if (offsetLastFine.isBefore(cm.getDateTime()) || oldFines.size() == 0) {
            if (cam.getEuroNorm() > lp.getEuroNumber()) {
                log.info(String.format("Licenseplate %s will receive an emission fine. cameraNorm=%d, carNorm=%d)", lp.getPlateId(), cam.getEuroNorm(), lp.getEuroNumber()));
                return new EmissionViolation(cam, lp.getEuroNumber(), lp, event.getCameraMessage());
            }
        }
        return null;
    }


    public void setTimeFrame(long timeFrame) {
        this.timeFrame = timeFrame;
    }
}
