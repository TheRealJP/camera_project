package be.kdg.processor.cameramessage.dto;

import lombok.Data;


@Data
public class RetryDTO {
    private int retryableAttempts;
    private int retryableTimeOut;

    public RetryDTO() {
    }
}
