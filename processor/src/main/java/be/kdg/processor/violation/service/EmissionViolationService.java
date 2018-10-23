package be.kdg.processor.violation.service;

import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.processor.violation.models.EmissionViolation;
import be.kdg.processor.violation.observerpattern.events.ConsumeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmissionViolationService implements ViolationService {
    private final Logger log = LoggerFactory.getLogger(EmissionViolationService.class);

    @Override
    public EmissionViolation checkViolation(ConsumeEvent event) {
        // TODO: checken op nummerplaat in de gequery'de messages of deze recent al een boete heeft gekregen en of deze binnen dat timeframe valt
        //TODO: query schrijven om berichten op te halen tov vorige dag
        Camera cam = event.getCamera();
        LicensePlate lp = event.getLp();

        if (cam.getEuroNorm() > lp.getEuroNumber()) {
            log.info(String.format("Licenseplate %s will receive a emission fine. cameraNorm=%d, carNorm=%d)", lp.getPlateId(), cam.getEuroNorm(), lp.getEuroNumber()));
            return new EmissionViolation(cam, lp.getEuroNumber(), lp, event.getCameraMessage());
        }
        return null;
    }
}
