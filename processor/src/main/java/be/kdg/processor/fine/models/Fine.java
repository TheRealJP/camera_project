package be.kdg.processor.fine.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Fines")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Long id;
    @Column
    private boolean approved;
    @OneToOne(targetEntity = FineFactor.class, cascade = CascadeType.ALL)
    private FineFactor fineFactor;
    @Column
    private double amount;
    @Column
    private String licensePlate;
    @Column
    private LocalDateTime fineDateTime;
    @Column
    private int cameraEmission;
    @Column
    private int carEmission;
    @Column
    private int speedLimit;
    @Column
    private int speed;

//    @OneToOne(targetEntity = Violation.class, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
//    private Violation violation;

    public Fine() {
    }

    public Fine(double amount) {
        this.amount = amount;
    }
}
