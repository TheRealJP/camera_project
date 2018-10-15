package be.kdg.processor.models.fine;

import be.kdg.processor.models.violations.Violation;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Fines")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

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
