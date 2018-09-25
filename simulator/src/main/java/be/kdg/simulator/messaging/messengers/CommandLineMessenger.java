package be.kdg.simulator.messaging.messengers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import be.kdg.simulator.generators.MessageGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "cli")
public class CommandLineMessenger implements Messenger {

    private final MessageGenerator messageGenerator;
    private final Logger log = LoggerFactory.getLogger(CommandLineMessenger.class);

    public CommandLineMessenger(MessageGenerator messageGenerator) { //intellij kan hier niet helemaal uit aan de configuratie
        this.messageGenerator = messageGenerator;
    }

    @Override
    @Schedules({@Scheduled(fixedDelayString = "#{${base.frequency}}"),
            @Scheduled(cron = "${morning.rush}"),
            @Scheduled(cron = "${evening.rush}")})
    public void sendMessage() {
        log.info("{}", messageGenerator.generateCameraMessage());
    }


}
