package be.kdg.processor.models.violations;

import lombok.*;
import org.springframework.stereotype.Service;

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

    @OneToOne(targetEntity = Violation.class, fetch = FetchType.EAGER)
    private Violation violation;

    public Fine() {
    }

    public Fine(double amount, Violation violation) {
        this.amount = amount;
        this.violation = violation;
    }
}
