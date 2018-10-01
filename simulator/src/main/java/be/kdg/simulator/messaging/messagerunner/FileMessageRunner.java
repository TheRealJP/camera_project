package be.kdg.simulator.messaging.messagerunner;

import be.kdg.simulator.models.CameraMessage;

import java.util.ArrayList;

public class FileMessageRunner implements MessageRunner {

    private final ArrayList<CameraMessage> cameraMessages;

    public FileMessageRunner(ArrayList<CameraMessage> cameraMessages) {
        this.cameraMessages = cameraMessages;
    }

    @Override
    public void doIt() {
        for (int i = 0; i < cameraMessages.size(); i++) {
            CameraMessage msg = cameraMessages.get(i);

        }
    }
}
