package be.kdg.processor.fine.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "Fines")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FINE_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private boolean approved;
    @Column
    private double amount;
    @Column
    private String licensePlate;
    @Column
    private LocalDateTime fineDateTime;


    public Fine() {
    }

    public Fine(double amount, String licensePlate, LocalDateTime fineDateTime) {
        this.amount = amount;
        this.licensePlate = licensePlate;
        this.fineDateTime = fineDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fine fine = (Fine) o;
        return approved == fine.approved &&
                Double.compare(fine.amount, amount) == 0 &&
                Objects.equals(id, fine.id) &&
                Objects.equals(licensePlate, fine.licensePlate) &&
                Objects.equals(fineDateTime, fine.fineDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, approved, amount, licensePlate, fineDateTime);
    }
}
