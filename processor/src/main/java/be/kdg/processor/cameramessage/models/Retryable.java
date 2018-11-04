package be.kdg.processor.cameramessage.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Retryable {
    @Id
    private Long id;
    @Column
    private int retryableAttempts;
    @Column
    private int retryableTimeOut;

    @Override
    public String toString() {
        return "Retryable{" +
                "id=" + id +
                ", retryableAttempts=" + retryableAttempts +
                ", retryableTimeOut=" + retryableTimeOut +
                '}';
    }
}
