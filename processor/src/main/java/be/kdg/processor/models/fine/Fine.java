package be.kdg.processor.models.fine;

import be.kdg.processor.models.violations.Violation;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Fines")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Long id;

    @Column
    private double finefactor;

    @Column
    private int speedLimit;

    @Column
    private int speed;

    @Column
    private boolean approved;

    @Column
    private double amount;

    @OneToOne(targetEntity = Violation.class, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Violation violation;

    public Fine() {
    }

    public Fine(double amount, Violation violation) {
        this.amount = amount;
        this.violation = violation;
    }
}
