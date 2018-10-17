package be.kdg.processor.service.violationservice;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.service.proxyservice.ProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;

@Service
//@Transactional //dwingt commit af
//TODO: query schrijven om berichten op te halen tov vorige dag
public class EmissionViolationService implements ViolationService {
    private final Logger log = LoggerFactory.getLogger(EmissionViolationService.class);
    private final ProxyService proxyService;

    public EmissionViolationService(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @Override
    public EmissionViolation checkViolation(CameraMessage cm, Collection<CameraMessage> cameraMessages) throws IOException {
        // TODO: checken op nummerplaat in de gequery'de messages of deze recent al een boete heeft gekregen en of deze binnen dat timeframe valt
//        for (CameraMessage cm : cameraMessages) {
        Camera cam = proxyService.collectCamera(cm.getCameraId());
        LicensePlate lp = proxyService.collectLicensePlate(cm.getLicensePlate());

        if (cam.getEuroNorm() > lp.getEuroNumber()) {
            log.info(String.format("Licenseplate %s will receive a emission fine. cameraNorm=%d, carNorm=%d)", lp.getPlateId(), cam.getEuroNorm(), lp.getEuroNumber()));
            return new EmissionViolation(cam.getEuroNorm(), lp.getEuroNumber(), lp.getPlateId());
        }
        return null;
    }


}
