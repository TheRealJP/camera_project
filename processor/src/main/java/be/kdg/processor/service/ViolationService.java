package be.kdg.processor.service;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.violations.Violation;

import java.io.IOException;

public interface ViolationService {
    Violation checkViolation(CameraMessage cm) throws IOException;
    Camera collectCamera(int camId) throws IOException;
    LicensePlate collectLicensePlate(CameraMessage cm) throws IOException;
    double calculateFine();
}
