package be.kdg.processor.models.violations;

import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import org.springframework.beans.factory.annotation.Value;

public class SpeedingViolation extends Violation {
    @Value("${timeframe.between.cameras}")
    private long timeFrame;
    private int speed;
    private LicensePlate lp;
    private Camera firstCamera;
    private Camera secondCamera;

    public SpeedingViolation(int speed, LicensePlate lp, Camera firstCamera, Camera secondCamera) {
        this.speed = speed;
        this.lp = lp;
        this.firstCamera = firstCamera;
        this.secondCamera = secondCamera;
    }
}
