package be.kdg.simulator.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public String toString() {
        return "CameraMessage{" +
                "cameraId=" + id +
                " | date=" + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " | time=" + dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                " | licensePlate='" + licensePlate + '\'' +
                '}';
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
