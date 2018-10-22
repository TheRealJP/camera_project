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
    private int factor;
    @Column
    private String violationType;

    public FineFactor() {
    }

    public FineFactor(int factor, String violationType) {
        this.factor = factor;
        this.violationType = violationType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FineFactor that = (FineFactor) o;
        return factor == that.factor &&
                Objects.equals(id, that.id) &&
                Objects.equals(violationType, that.violationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, factor, violationType);
    }
}
