package be.kdg.processor.models.violations;

import be.kdg.processor.models.messages.CameraMessage;
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

    @OneToOne(targetEntity = LicensePlate.class)
    protected LicensePlate licensePlate;

    Violation() { }
    Violation(CameraMessage cm, LicensePlate lp) {
        this.licensePlate = lp;
        this.message = cm;
    }
    Violation(Segment segment, CameraMessage cm, LicensePlate lp) {
        this.segment = segment;
        this.licensePlate = lp;
        this.message = cm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Violation violation = (Violation) o;
        return id == violation.id &&
                Objects.equals(segment, violation.segment) &&
                Objects.equals(message, violation.message) &&
                Objects.equals(licensePlate, violation.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, segment, message, licensePlate);
    }
}
