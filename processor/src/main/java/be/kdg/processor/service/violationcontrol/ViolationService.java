package be.kdg.processor.service.violationcontrol;

import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;

import java.io.IOException;

public interface ViolationService {
    void checkViolation() throws IOException;

    Camera collectCamera(int camId) throws IOException;

    LicensePlate collectLicensePlate(String cm) throws IOException;
}
