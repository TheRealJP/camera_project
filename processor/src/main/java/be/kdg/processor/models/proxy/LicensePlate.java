package be.kdg.processor.models.proxy;

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
@Entity
@Table(name = "LicensePlates")
public class LicensePlate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String plateId; // convert from string to long
    @Column
    private String nationalNumber;
    @Column
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
