package be.kdg.processor.models.messages;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Data
@Entity
@Table(name = "CameraMessages")
public class CameraMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tableId;
    @Column
    private int cameraId;
    @Column
    private LocalDateTime dateTime;
    @Column
    private String licensePlate;

    public CameraMessage() {
    }

    public CameraMessage(int cameraId, LocalDateTime dateTime, String licensePlate ) {
        this.cameraId = cameraId;
        this.dateTime = dateTime;
        this.licensePlate = licensePlate;
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
    public String toString() {
        return String.format("%2d, %s, %s", cameraId,
                licensePlate,
                dateTime
        );
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
