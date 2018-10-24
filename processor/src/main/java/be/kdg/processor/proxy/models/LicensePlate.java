package be.kdg.processor.proxy.models;

import lombok.Data;

import javax.persistence.*;


@Data
@Table(name = "LicensePlates")
public class LicensePlate {
    private Long id;
    private String plateId; // convert from string to long
    private String nationalNumber;
    private int euroNumber;

    public LicensePlate() {
    }

    public LicensePlate(String plateId, String nationalNumber, int euroNumber) {
        this.plateId = plateId;
        this.nationalNumber = nationalNumber;
        this.euroNumber = euroNumber;
    }

    @Override
    public String toString() {
        return "LicensePlate{" +
                "plateId='" + plateId + '\'' +
                ", nationalNumber='" + nationalNumber + '\'' +
                ", euroNumber=" + euroNumber +
                '}';
    }
}
