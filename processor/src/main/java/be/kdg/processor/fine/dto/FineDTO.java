package be.kdg.processor.fine.dto;

import be.kdg.processor.violation.models.Violation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FineDTO {
    private int amount;
    private Violation violation;
    private int speedLimit;
    private double finefactor;
    private int speed;
    private boolean approved;

    public FineDTO() {
    }
}
