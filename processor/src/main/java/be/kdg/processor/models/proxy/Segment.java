package be.kdg.processor.models.proxy;


//segment":{"connectedCameraId":5,"distance":550,"speedLimit":50
public class Segment {
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
