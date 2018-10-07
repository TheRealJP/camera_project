package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "cli")
public class CommandLineMessenger implements Messenger {

    private final Logger log = LoggerFactory.getLogger(CommandLineMessenger.class);

    @Override
    public void sendMessage(CameraMessage message) {
        log.info("cli cameramessage: " + message.toString());
    }
}
