package be.kdg.processor.dto;

import be.kdg.processor.models.violations.Violation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Empty;

//@NoArgsConstructor //data via 't web json --> automatisch omzetten naar een object
@Data
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FineDTO {
    private int amount;
    private Violation violation;
    private int speedLimit;
    private double finefactor;
    private int speed;

    public FineDTO() {
    }
}
