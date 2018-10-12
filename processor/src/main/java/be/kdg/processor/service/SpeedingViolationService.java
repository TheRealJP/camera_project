package be.kdg.processor.service;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.SpeedingViolation;
import be.kdg.processor.models.violations.Violation;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Component
public class SpeedingViolationService implements ViolationService {

    private final CameraServiceProxy camProxy;
    private final LicensePlateServiceProxy lpProxy;
    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(SpeedingViolationService.class);

    public SpeedingViolationService(CameraServiceProxy camProxy, ObjectMapper objectMapper, LicensePlateServiceProxy lpProxy) {
        this.camProxy = camProxy;
        this.objectMapper = objectMapper;
        this.lpProxy = lpProxy;
    }

    @Override
    public Violation checkViolation(CameraMessage cm) throws IOException {
        try {
            Camera camera = collectCamera(cm.getId());
            Camera otherCamera = collectCamera(camera.getSegment().getConnectedCameraId());
            System.out.println("main camera:" + camera.getSegment().getDistance());

        } catch (IOException | LicensePlateNotFoundException | CameraNotFoundException | NullPointerException e) {
            log.error(e.getMessage());
        }

        return new SpeedingViolation();
    }


    public double calculateFine() {

        return 0.;
    }

    private double calculateDistance() {
        return 0.;
    }

    public Camera collectCamera(int camId) throws IOException {
        String camJson = camProxy.get(camId);
        log.info(camJson);
        return objectMapper.readValue(camJson, Camera.class);
    }

    public LicensePlate collectLicensePlate(CameraMessage cm) throws IOException {
        String lpJson = lpProxy.get(cm.getLicensePlate());
        log.info(lpJson);
        return objectMapper.readValue(lpJson, LicensePlate.class);
    }
}
