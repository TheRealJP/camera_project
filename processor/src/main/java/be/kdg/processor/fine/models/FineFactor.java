package be.kdg.processor.fine.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FineFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private int id;
    @Column
    private int factor;
    @Column
    private String violationType;

    public FineFactor() {
    }

    public FineFactor(int factor, String violationType) {
        this.factor = factor;
        this.violationType = violationType;
    }
}
