package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.generators.RandomMessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CommandLineMessenger implements Messenger {
    private final MessageGenerator messageGenerator;

    public CommandLineMessenger(MessageGenerator messageGenerator) { //intellij kan hier niet helemaal uit aan de configuratie
        this.messageGenerator = messageGenerator;
    }

    @Override
    @Scheduled(fixedDelay = 1000L)
    public void sendMessage() {
        System.out.println(messageGenerator.generateCameraMessage());
    }
}
