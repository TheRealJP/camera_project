package be.kdg.processor.fine.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FineFactorDTO {
    private int speedFactor;
    private int emissionFactor;

    public FineFactorDTO() {
    }


}
