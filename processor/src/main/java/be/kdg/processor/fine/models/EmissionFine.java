package be.kdg.processor.fine.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@DiscriminatorValue("emissionfine")
public class EmissionFine extends Fine {
    @Column
    private int cameraEmission;
    @Column
    private int carEmission;

    public EmissionFine() {
    }

    public EmissionFine(double amount, String licensePlate, LocalDateTime fineDateTime, int cameraEmission, int carEmission) {
        super(amount, licensePlate, fineDateTime);
        this.cameraEmission = cameraEmission;
        this.carEmission = carEmission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmissionFine that = (EmissionFine) o;
        return cameraEmission == that.cameraEmission &&
                carEmission == that.carEmission;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cameraEmission, carEmission);
    }
}
