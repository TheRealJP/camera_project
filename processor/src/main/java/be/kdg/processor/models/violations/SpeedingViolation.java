package be.kdg.processor.models.violations;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.proxy.Segment;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Data
@Entity
@DiscriminatorValue("speeding")
public class SpeedingViolation extends Violation {
    @Column
    private final int speedLimit;
    @Column
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
