package be.kdg.processor.models.violations;


import org.springframework.beans.factory.annotation.Value;

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
    public String toString() {
        return "EmissionViolation{" +
                "cameraEuroNorm=" + cameraEuroNorm +
                ", licensePlateEuroNorm=" + licensePlateEuroNorm +
                ", licensePlate='" + licensePlate + '\'' +
                '}';
    }
}
