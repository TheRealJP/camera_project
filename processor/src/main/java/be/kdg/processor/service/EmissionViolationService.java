package be.kdg.processor.service;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.Violation;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EmissionViolationService implements ViolationService {

    private final CameraServiceProxy camProxy;
    private final LicensePlateServiceProxy lpProxy;
    private final ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(SpeedingViolationService.class);

    public EmissionViolationService(CameraServiceProxy camProxy, LicensePlateServiceProxy lpProxy, ObjectMapper objectMapper) {
        this.camProxy = camProxy;
        this.lpProxy = lpProxy;
        this.objectMapper = objectMapper;
    }

    @Override
    public Violation checkViolation(CameraMessage cm) {

        try {
            Camera camera = collectCamera(cm.getId());
            LicensePlate lp = collectLicensePlate(cm);
            if (camera.getEuroNorm() > lp.getEuroNumber()) {
                log.info(String.format("Licenseplate %s will receive a emission fine. cameraNorm=%d, carNorm=%d)", lp.getPlateId(), camera.getEuroNorm(), lp.getEuroNumber()));
                return new EmissionViolation(camera.getEuroNorm(), lp.getEuroNumber(), lp.getPlateId());
            }

        } catch (IOException | LicensePlateNotFoundException | CameraNotFoundException e) {
            log.error(e.getMessage());
        }

        return null;
    }


    @Override
    public double calculateFine() {
        return 0;
    }

    @Override
    public Camera collectCamera(int camId) throws IOException {
        String camJson = camProxy.get(camId);
        return objectMapper.readValue(camJson, Camera.class);
    }

    @Override
    public LicensePlate collectLicensePlate(CameraMessage cm) throws IOException {
        String lpJson = lpProxy.get(cm.getLicensePlate());
        return objectMapper.readValue(lpJson, LicensePlate.class);
    }

}
