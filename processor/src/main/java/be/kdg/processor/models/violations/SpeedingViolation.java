package be.kdg.processor.models.violations;

import org.springframework.beans.factory.annotation.Value;

public class SpeedingViolation extends Violation{
    @Value("${timeframe.between.cameras}")
    private long timeFrame;
}
