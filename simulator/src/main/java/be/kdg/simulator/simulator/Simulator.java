package be.kdg.simulator.simulator;

import be.kdg.simulator.exceptions.MessageNotSentException;
import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.messaging.messengers.Messenger;
import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Simulator {
    private static final Logger log = LoggerFactory.getLogger(Simulator.class);
    private static MessageGenerator messageGenerator;
    private static Messenger messenger;


    public Simulator(MessageGenerator messageGenerator, Messenger messenger) {
        Simulator.messageGenerator = messageGenerator;
        Simulator.messenger = messenger;
    }

    public static void runSimulator() {
        try {
            CameraMessage msg = messageGenerator.generateCameraMessage();
            while (msg != null) { // als file uitgelezen is , random blijft gaan dus overlapt
                messenger.sendMessage(msg);
                msg = messageGenerator.generateCameraMessage();
            }

        } catch (InterruptedException | MessageNotSentException e) {
            log.error(e.getMessage());
        }
    }

}
