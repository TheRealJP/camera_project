package be.kdg.processor.models.proxy;
// {"cameraId":4,"location":{"lat":51.203512,"long":4.437337},"segment":{"connectedCameraId":5,"distance":550,"speedLimit":50},"euroNorm":3}
public class Camera {
    private int cameraId;
    private Location location;
    private Segment segment;
    private int euroNorm;

    public Camera() {
    }

    public Camera(int cameraId) {
        this.cameraId = cameraId;
    }

    public Camera(int cameraId, Location location) {
        this.cameraId = cameraId;
        this.location = location;
    }

    public Camera(int cameraId, Location location, Segment segment) {
        this.cameraId = cameraId;
        this.location = location;
        this.segment = segment;
    }

    public Camera(int cameraId, Location location, Segment segment, int euroNorm) {
        this.cameraId = cameraId;
        this.location = location;
        this.segment = segment;
        this.euroNorm = euroNorm;
    }

    public int getCameraId() {
        return cameraId;
    }

    public Location getLocation() {
        return location;
    }

    public Segment getSegment() {
        return segment;
    }

    public int getEuroNorm() {
        return euroNorm;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "cameraId=" + cameraId +
                ", location=" + location +
                ", segment=" + segment +
                ", euroNorm=" + euroNorm +
                '}';
    }
}
