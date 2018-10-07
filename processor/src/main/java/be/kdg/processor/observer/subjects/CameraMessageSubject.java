package be.kdg.processor.observer.subjects;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.observer.Observer;
import be.kdg.processor.observer.Subject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CameraMessageSubject implements Subject {

    private final ArrayList<Observer> observers;
    private final ArrayList<CameraMessage> messages;

    public CameraMessageSubject(ArrayList<Observer> observers, ArrayList<CameraMessage> messages) {
        this.observers = observers;
        this.messages = messages;
    }

    public ArrayList<CameraMessage> getMessages() {
        return messages;
    }

    @Override
    public void addMessage(CameraMessage cm) {
        messages.add(cm);
        notifyObserver();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver() {
        for (Observer o : observers) {
            o.update(this);
        }
    }
}
