package be.kdg.processor.fine.models;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Objects;


@Data
@Entity
@DiscriminatorValue("speedfine")
public class SpeedFine extends Fine {
    @Column
    private int speedLimit;
    @Column
    private int speed;

    public SpeedFine() {
    }

    public SpeedFine(double amount, String licensePlate, LocalDateTime fineDateTime, int speedLimit, int speed) {
        super(amount, licensePlate, fineDateTime);
        this.speedLimit = speedLimit;
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpeedFine speedFine = (SpeedFine) o;
        return speedLimit == speedFine.speedLimit &&
                speed == speedFine.speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speedLimit, speed);
    }
}
