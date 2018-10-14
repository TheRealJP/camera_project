package be.kdg.processor.models.proxy;


import lombok.Data;

import javax.persistence.*;

//segment":{"connectedCameraId":5,"distance":550,"speedLimit":50
@Data
@Entity
@Table(name = "Segments")
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private int connectedCameraId;
    @Column
    private int distance;
    @Column
    private int speedLimit;

    public Segment() {
    }

    public Segment(int connectedCameraId, int distance, int speedLimit) {
        this.connectedCameraId = connectedCameraId;
        this.distance = distance;
        this.speedLimit = speedLimit;
    }

    public int getConnectedCameraId() {
        return connectedCameraId;
    }

    public int getDistance() {
        return distance;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "connectedCameraId=" + connectedCameraId +
                ", distance=" + distance +
                ", speedLimit=" + speedLimit +
                '}';
    }
}
