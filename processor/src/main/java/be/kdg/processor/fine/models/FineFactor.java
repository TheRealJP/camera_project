package be.kdg.processor.fine.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class FineFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Long id;
    @Column
    private int speedfactor;
    @Column
    private int emissionfactor;


    public FineFactor() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FineFactor that = (FineFactor) o;
        return speedfactor == that.speedfactor &&
                emissionfactor == that.emissionfactor &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, speedfactor, emissionfactor);
    }
}
