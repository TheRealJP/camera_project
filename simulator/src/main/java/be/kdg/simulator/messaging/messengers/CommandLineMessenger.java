package be.kdg.simulator.messaging.messengers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import be.kdg.simulator.generators.MessageGenerator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;


@Component
public class CommandLineMessenger implements Messenger {
    private final MessageGenerator messageGenerator;
    private final Logger log = LoggerFactory.getLogger(CommandLineMessenger.class);

    @Override
    @Schedules({@Scheduled(fixedDelayString = "#{${base.frequency}}"),
                @Scheduled(cron = "${morning.rush}"),
                @Scheduled(cron = "${evening.rush}")})
    public void sendMessage() {
        System.out.println(messageGenerator.generateCameraMessage());
//        log.warn("{}",messageGenerator.generateCameraMessage() );
    }

    public CommandLineMessenger(MessageGenerator messageGenerator) { //intellij kan hier niet helemaal uit aan de configuratie
        this.messageGenerator = messageGenerator;
    }
}
