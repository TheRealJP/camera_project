package be.kdg.processor.observer;

import be.kdg.processor.observer.subjects.CameraMessageSubject;

public interface Observer {
    void update(Object object);
}
