package be.kdg.processor.models.violations;


import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.LicensePlate;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

//https://hibernate.atlassian.net/browse/HHH-12414
@Data
@Entity
@DiscriminatorValue("emission")
//@AllArgsConstructor
public class EmissionViolation extends Violation {
    @Column
    private int cameraEuroNorm;
    @Column
    private int licensePlateEuroNorm;

    public EmissionViolation(int cameraEuroNorm, int licensePlateEuroNorm, LicensePlate licensePlate, CameraMessage cm) {
        super(cm,licensePlate);
        this.cameraEuroNorm = cameraEuroNorm;
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
