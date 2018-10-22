package be.kdg.processor.violation.models;

import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.processor.proxy.models.Segment;
import lombok.Data;

import java.util.Objects;

@Data
public class SpeedingViolation extends Violation {
    private final int speedLimit;
    private int speed;

    public SpeedingViolation(int speed, int speedLimit, LicensePlate lp, Camera firstCamera, CameraMessage cm, Segment segment) {
        super(segment, cm, lp, firstCamera);
        this.speed = speed;
        this.speedLimit = speedLimit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpeedingViolation that = (SpeedingViolation) o;
        return speedLimit == that.speedLimit &&
                speed == that.speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speedLimit, speed);
    }

    @Override
    public String
    toString() {
        return "SpeedingViolation{" +
                "speed=" + speed + '}';
    }
}
