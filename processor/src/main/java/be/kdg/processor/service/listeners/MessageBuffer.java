package be.kdg.processor.service.listeners;

import be.kdg.processor.models.messages.CameraMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MessageBuffer {
    private final ArrayList<CameraMessage> cameraMessages;

    public MessageBuffer(ArrayList<CameraMessage> cameraMessages) {
        this.cameraMessages = cameraMessages;
    }

    public ArrayList<CameraMessage> getCameraMessages() {
        return cameraMessages;
    }

    public void addMessage(CameraMessage cm) {
        cameraMessages.add(cm);
    }


}
