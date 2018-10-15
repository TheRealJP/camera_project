package be.kdg.processor.models.violations;

import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.proxy.Location;
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
    @OneToOne(targetEntity = Camera.class)
    private Camera secondCamera;


    public SpeedingViolation(int speed, LicensePlate lp, Camera firstCamera, Camera secondCamera) {
        this.speed = speed;
        this.firstCamera = firstCamera;
        this.secondCamera = secondCamera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpeedingViolation that = (SpeedingViolation) o;
        return speed == that.speed &&
                Objects.equals(firstCamera, that.firstCamera) &&
                Objects.equals(secondCamera, that.secondCamera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speed, firstCamera, secondCamera);
    }

    @Override
    public String
    toString() {
        return "SpeedingViolation{" +
                "speed=" + speed +
                ", firstCamera=" + firstCamera +
                ", secondCamera=" + secondCamera +
                '}';
    }
}
