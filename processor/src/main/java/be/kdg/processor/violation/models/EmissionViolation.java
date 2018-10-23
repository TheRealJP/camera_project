package be.kdg.processor.violation.models;


import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import lombok.Data;

import java.util.Objects;

@Data
public class EmissionViolation extends Violation {
    private int cameraEuroNorm;
    private int licensePlateEuroNorm;

    public EmissionViolation(Camera cameraEuroNorm, int licensePlateEuroNorm, LicensePlate licensePlate, CameraMessage cm) {
        super(cm, licensePlate,cameraEuroNorm);
        this.cameraEuroNorm = cameraEuroNorm.getEuroNorm();
        this.licensePlateEuroNorm = licensePlateEuroNorm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmissionViolation that = (EmissionViolation) o;
        return cameraEuroNorm == that.cameraEuroNorm &&
                licensePlateEuroNorm == that.licensePlateEuroNorm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cameraEuroNorm, licensePlateEuroNorm);
    }

    @Override
    public String toString() {
        return "EmissionViolation{" +
                "cameraEuroNorm=" + cameraEuroNorm +
                ", licensePlateEuroNorm=" + licensePlateEuroNorm +
                '}';
    }
}
