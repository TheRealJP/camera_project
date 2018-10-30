package be.kdg.processor.violation.models;

import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.processor.proxy.models.Segment;
import lombok.Data;

import java.util.Objects;

/**
 * Create a new subclass by extending the violation class
 */
@Data
public abstract class Violation {
    protected long id;
    protected Segment segment;
    protected CameraMessage message;
    protected Camera cam;
    protected LicensePlate licensePlate;
    protected String VIOLATION_TYPE;


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
