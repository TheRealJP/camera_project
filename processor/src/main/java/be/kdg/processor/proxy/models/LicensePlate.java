package be.kdg.processor.proxy.models;

import lombok.Data;

import javax.persistence.*;

//-10-05 11:44:12.646  INFO 16456 --- [cTaskExecutor-1] b.k.p.service.CameraServiceUtility       : {"plateId":"4-ABC-123","nationalNumber":"69.05.22-123.4","euroNumber":4}
//        2018-10-05 11:44:12.646  INFO 16456 --- [cTaskExecutor-1] b.k.p.messaging.consumers.QueueConsumer  : Message received:  1, 4-ABC-123, 2018-10-05T11:44:00.588247
//        2018-10-05 11:44:12.858  INFO 16456 --- [cTaskExecutor-1] b.k.p.service.CameraServiceUtility       : {"plateId":"1-ABC-123","nationalNumber":"69.05.22-123.1","euroNumber":1}
//        2018-10-05 11:44:12.858  INFO 16456 --- [cTaskExecutor-1] b.k.p.messaging.consumers.QueueConsumer  : Message received:  3, 1-ABC-123, 2018-10-05T11:44:01.588247
//        2018-10-05 11:44:13.062  INFO 16456 --- [cTaskExecutor-1] b.k.p.service.CameraServiceUtility       : {"plateId":"2-ABC-123","nationalNumber":"69.05.22-123.2","euroNumber":2}
//        2018-10-05 11:44:13.062  INFO 16456 --- [cTaskExecutor-1] b.k.p.messaging.consumers.QueueConsumer  : Message received:  4, 2-ABC-123, 2018-10-05T11:44:01.688247
//        2018-10-05 11:44:13.270  INFO 16456 --- [cTaskExecutor-1] b.k.p.messaging.consumers.QueueConsumer  : Message received:  5, 2-ABC-123, 2018-10-05T11:44:03.688247
//        2018-10-05 11:44:13.474  INFO 16456 --- [cTaskExecutor-1] b.k.p.service.CameraServiceUtility       : {"plateId":"4-ABC-123","nationalNumber":"69.05.22-123.4","euroNumber":4}
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateId() {
        return plateId;
    }

    public void setPlateId(String plateId) {
        this.plateId = plateId;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public int getEuroNumber() {
        return euroNumber;
    }

    public void setEuroNumber(int euroNumber) {
        this.euroNumber = euroNumber;
    }
}
