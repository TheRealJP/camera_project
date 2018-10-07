package be.kdg.processor.observer;

import be.kdg.processor.models.CameraMessage;

public interface Subject {
    void addObserver(Observer o);
    void deleteObserver(Observer o);
    void notifyObserver();
    void addMessage(CameraMessage message);
}
