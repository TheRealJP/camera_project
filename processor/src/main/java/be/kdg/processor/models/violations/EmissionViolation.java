package be.kdg.processor.models.violations;


import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

public class EmissionViolation extends Violation {
    private int cameraEuroNorm;
    private int licensePlateEuroNorm;
    private String licensePlate;
    @Value("${2}")
    private double emissionFineFactor;

    public EmissionViolation(int cameraEuroNorm, int licensePlateEuroNorm, String licensePlate) {
        this.cameraEuroNorm = cameraEuroNorm;
        this.licensePlateEuroNorm = licensePlateEuroNorm;
        this.licensePlate = licensePlate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmissionViolation that = (EmissionViolation) o;
        return cameraEuroNorm == that.cameraEuroNorm &&
                licensePlateEuroNorm == that.licensePlateEuroNorm &&
                Double.compare(that.emissionFineFactor, emissionFineFactor) == 0 &&
                Objects.equals(licensePlate, that.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cameraEuroNorm, licensePlateEuroNorm, licensePlate, emissionFineFactor);
    }

    @Override
    public String toString() {
        return "EmissionViolation{" +
                "cameraEuroNorm=" + cameraEuroNorm +
                ", licensePlateEuroNorm=" + licensePlateEuroNorm +
                ", licensePlate='" + licensePlate + '\'' +
                '}';
    }
}
