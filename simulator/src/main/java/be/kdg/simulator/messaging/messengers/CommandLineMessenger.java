package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import be.kdg.simulator.generators.MessageGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "cli")
public class CommandLineMessenger implements Messenger {
    private final MessageGenerator messageGenerator;
    private final Logger log = LoggerFactory.getLogger(CommandLineMessenger.class);

    public CommandLineMessenger(MessageGenerator messageGenerator) { //intellij kan hier niet helemaal uit aan de configuratie, maar werkt wel :)
        this.messageGenerator = messageGenerator;
    }

    /**
     * #  Doel: scheduling op default laten draaien als filemode aan staat
     * 1. zorgen voor een hele lange delay, 1 methode (-)
     * 2. sendmessage delegeren, nieuwe methodes van eigen scheduling voorzien (+)
     */
    @PostConstruct // anders error: beans met void niet toegelaten
    @Schedules({@Scheduled(fixedDelayString = "#{${base.frequency}}"),
            @Scheduled(cron = "${morning.rush}"),
            @Scheduled(cron = "${evening.rush}")})
    @ConditionalOnProperty(name = "generator.type", havingValue = "random")
    public void sendRandomMessages() {
        sendMessage();
    }

    @PostConstruct // anders error: beans met void niet toegelaten
    @Scheduled(fixedDelay = 1000L)
    @ConditionalOnProperty(name = "generator.type", havingValue = "file")
    public void sendFileMessages() {
        sendMessage();
    }

    @Override
    public void sendMessage() {
        CameraMessage message = messageGenerator.generateCameraMessage();
        if (message == null)
            System.exit(0);
        log.info(message.toString());

    }


}
