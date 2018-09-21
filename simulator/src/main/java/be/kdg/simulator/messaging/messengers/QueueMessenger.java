package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueMessenger implements Messenger {
    private final MessageGenerator messageGenerator;

    public QueueMessenger(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    @Override
    public void sendMessage() {
        System.out.println(messageGenerator.generateCameraMessage());
    }
}
