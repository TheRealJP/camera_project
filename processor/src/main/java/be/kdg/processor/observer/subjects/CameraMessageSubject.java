package be.kdg.processor.observer.subjects;

import be.kdg.processor.models.CameraMessage;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CameraMessageSubject extends java.util.Observable implements Observable {

    private final ArrayList<InvalidationListener> listeners;
    private final ArrayList<CameraMessage> messages;

    public CameraMessageSubject(ArrayList<InvalidationListener> listeners, ArrayList<CameraMessage> messages) {
        this.listeners = listeners;
        this.messages = messages;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }
}
