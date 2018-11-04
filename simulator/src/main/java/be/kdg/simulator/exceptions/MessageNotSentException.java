package be.kdg.simulator.exceptions;

public class MessageNotSentException extends Throwable {
    public MessageNotSentException(String message, Throwable cause) {
        super(message, cause);
    }
}
