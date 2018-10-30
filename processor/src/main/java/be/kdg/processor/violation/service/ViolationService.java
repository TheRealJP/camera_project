package be.kdg.processor.violation.service;

import be.kdg.processor.violation.models.Violation;
import be.kdg.processor.violation.observerpattern.events.ConsumeEvent;

import java.io.IOException;

/**
 * creating a new service:
 * ----------------------
 * Create a new subclass by extending the violation class
 * create a new violationservice subclass in violation.service and implement this interface to make a new implementation.
 * create a new ViolationListener in violation.observerpattern.listeners
 * create a new Fine subclass for your violation
 * create a new method in the fine.service.FineService class to calculate your fine

 * speedviolation_service:
 * ----------------------
 * for each message, check if theres another message with the other camera cameraId and its about the same licenseplate
 * check if the message is about the same licenseplate AND the same other camera
 * using the messages of the last , for example, 30 minutes. The timeframe can be adjusted.

 * emission_service:
 * ----------------------
 * for each message check if the norm of the car is lower than that of the camera who registered the message.
 */
public interface ViolationService {
    Violation checkViolation(ConsumeEvent event) throws IOException;
}
