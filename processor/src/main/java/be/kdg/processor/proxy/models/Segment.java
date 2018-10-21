package be.kdg.processor.proxy.models;


import lombok.Data;

import javax.persistence.Table;

//segment":{"connectedCameraId":5,"distance":550,"speedLimit":50
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConnectedCameraId() {
        return connectedCameraId;
    }

    public void setConnectedCameraId(int connectedCameraId) {
        this.connectedCameraId = connectedCameraId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }
}
