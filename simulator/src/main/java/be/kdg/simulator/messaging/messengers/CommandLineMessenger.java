package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.generators.RandomMessageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;


@Component
public class CommandLineMessenger implements Messenger {
    private final MessageGenerator messageGenerator;
    private final Logger log = LoggerFactory.getLogger(CommandLineMessenger.class);

    @Override
    @Schedules(value = {@Scheduled(fixedDelayString = "#{${base.frequency}}") /*default delay*/,
            @Scheduled(cron = "${morning.rush}")/*morning rush*/,
            @Scheduled(cron = "${evening.rush}")/*evening rush*/})
    public void sendMessage() {
        System.out.println(messageGenerator.generateCameraMessage());
//        log.warn("{}",messageGenerator.generateCameraMessage() );
    }

    public CommandLineMessenger(MessageGenerator messageGenerator) { //intellij kan hier niet helemaal uit aan de configuratie
        this.messageGenerator = messageGenerator;
    }
}
