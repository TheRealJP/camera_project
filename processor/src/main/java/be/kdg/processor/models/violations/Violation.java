package be.kdg.processor.models.violations;

import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.Segment;
import lombok.Data;

import javax.persistence.*;
import java.util.Observer;

@Data
@Entity
@Table(name = "Violations")
public abstract class Violation  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(targetEntity = Segment.class)
    private Segment segment;

    public Violation() {
    }
}
