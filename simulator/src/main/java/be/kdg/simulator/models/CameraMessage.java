package be.kdg.simulator.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class CameraMessage {

    private int cameraId;
    private LocalDateTime dateTime;
    private String licensePlate;

    public CameraMessage(int cameraId, LocalDateTime dateTime, String licensePlate) {
        this.cameraId = cameraId;
        this.dateTime = dateTime;
        this.licensePlate = licensePlate;
    }

    public CameraMessage() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CameraMessage)) return false;
        CameraMessage that = (CameraMessage) o;
        return cameraId == that.cameraId &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(licensePlate, that.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cameraId, dateTime, licensePlate);
    }

    @Override
    //TODO: tijd en datum terug samen om parsen makkelijker te maken
    public String toString() {
        return String.format("%2d, %s, %s", cameraId,
                licensePlate,
                dateTime
        );
    }

    public int getCameraId() {
        return cameraId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
