package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.messaging.messagerunner.MessageRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
public class QueueMessenger implements Messenger {

    private final MessageRunner messageRunner;

    public QueueMessenger(MessageRunner messageRunner) {
        this.messageRunner = messageRunner;
    }

    @Override
    public void sendMessage() {
        messageRunner.messageBuffering();
    }
}
