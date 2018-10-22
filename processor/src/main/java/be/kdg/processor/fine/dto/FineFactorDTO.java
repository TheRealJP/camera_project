package be.kdg.processor.fine.dto;

import lombok.Data;

@Data
public class FineFactorDTO {
    private int factor;
    private String violationType;

    public FineFactorDTO() {
    }
}
