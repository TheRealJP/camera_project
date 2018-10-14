package be.kdg.processor.models.violations;

import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

@Data
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




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeedingViolation that = (SpeedingViolation) o;
        return speed == that.speed &&
                Objects.equals(lp, that.lp) &&
                Objects.equals(firstCamera, that.firstCamera) &&
                Objects.equals(secondCamera, that.secondCamera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speed, lp, firstCamera, secondCamera);
    }
}
