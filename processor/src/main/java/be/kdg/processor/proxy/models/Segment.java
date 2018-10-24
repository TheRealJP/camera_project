package be.kdg.processor.proxy.models;


import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "Segments")
public class Segment {
    private int id;
    private int connectedCameraId;
    private int distance;
    private int speedLimit;

    public Segment() {
    }

    public Segment(int connectedCameraId, int distance, int speedLimit) {
        this.connectedCameraId = connectedCameraId;
        this.distance = distance;
        this.speedLimit = speedLimit;
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
