package be.kdg.processor.models.violations;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.proxy.Segment;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@Data
@Entity
@DiscriminatorValue("speed")
public class SpeedingViolation extends Violation {
    @Column
    private int speed;
    @OneToOne(targetEntity = Camera.class)
    private Camera firstCamera;


    public SpeedingViolation(int speed, LicensePlate lp, Camera firstCamera, CameraMessage cm, Segment segment) {
        super(segment, cm, lp);
        this.speed = speed;
        this.firstCamera = firstCamera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpeedingViolation that = (SpeedingViolation) o;
        return speed == that.speed &&
                Objects.equals(firstCamera, that.firstCamera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speed, firstCamera);
    }

    @Override
    public String
    toString() {
        return "SpeedingViolation{" +
                "speed=" + speed +
                ", firstCamera=" + firstCamera +
                '}';
    }
}
