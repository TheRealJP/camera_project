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
public class Retryable {
    @Id
    private Long id;
    @Column
    private int retryableAttempts;
    @Column
    private int retryableTimeOut;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRetryableAttempts() {
        return retryableAttempts;
    }

    public void setRetryableAttempts(int retryableAttempts) {
        this.retryableAttempts = retryableAttempts;
    }

    public int getRetryableTimeOut() {
        return retryableTimeOut;
    }

    public void setRetryableTimeOut(int retryableTimeOut) {
        this.retryableTimeOut = retryableTimeOut;
    }
}
