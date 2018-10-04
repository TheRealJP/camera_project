package be.kdg.simulator.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class CameraMessage {

    private int id;
    private LocalDateTime dateTime;
    private String licensePlate;

    public CameraMessage(int id, LocalDateTime dateTime, String licensePlate) {
        this.id = id;
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
        return id == that.id &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(licensePlate, that.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, licensePlate);
    }

    @Override
    //TODO: tijd en datum terug samen om parsen makkelijker te maken
    public String toString() {
        return String.format("%2d, %s, %s", id,
                licensePlate,
                dateTime
        );
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
