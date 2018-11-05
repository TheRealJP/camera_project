package be.kdg.processor.violation.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class TimeFrame {
    @Id
    private Long id;
    @Column
    private int speedTimeFrame;
    @Column
    private int emissionTimeFrame;
}
