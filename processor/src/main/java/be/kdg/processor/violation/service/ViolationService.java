package be.kdg.processor.violation.service;

import be.kdg.processor.violation.models.Violation;
import be.kdg.processor.violation.observerpattern.events.ConsumeEvent;

import java.io.IOException;


public interface ViolationService {
    Violation checkViolation(ConsumeEvent event) throws IOException;
}
