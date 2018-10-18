package be.kdg.processor.models.violations;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.proxy.Segment;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "Violations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VIOLATION_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Violation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @OneToOne(targetEntity = Segment.class, cascade = {CascadeType.ALL})
    protected Segment segment;

    @OneToOne(targetEntity = CameraMessage.class, cascade = {CascadeType.ALL})
    protected CameraMessage message;

    @OneToOne(targetEntity = Camera.class, cascade = {CascadeType.ALL})
    protected Camera cam;

    @OneToOne(targetEntity = LicensePlate.class, cascade = {CascadeType.ALL})
    protected LicensePlate licensePlate;

    Violation() {
    }

    Violation(CameraMessage cm, LicensePlate lp, Camera cam) {
        this.licensePlate = lp;
        this.message = cm;
        this.cam = cam;
    }

    Violation(Segment segment, CameraMessage cm, LicensePlate lp, Camera cam) {
        this.segment = segment;
        this.licensePlate = lp;
        this.message = cm;
        this.cam = cam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Violation violation = (Violation) o;
        return id == violation.id &&
                Objects.equals(segment, violation.segment) &&
                Objects.equals(message, violation.message) &&
                Objects.equals(cam, violation.cam) &&
                Objects.equals(licensePlate, violation.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, segment, message, cam, licensePlate);
    }
}
