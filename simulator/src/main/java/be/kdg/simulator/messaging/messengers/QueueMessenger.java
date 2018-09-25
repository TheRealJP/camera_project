package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@ConditionalOnProperty(havingValue = "queue", name = "messenger.type")
public class QueueMessenger implements Messenger {
    private final MessageGenerator messageGenerator;
    private final RabbitTemplate template;
    private final Queue queue;


    public QueueMessenger(MessageGenerator messageGenerator, RabbitTemplate template, Queue queue) {
        this.messageGenerator = messageGenerator;
        this.template = template;
        this.queue = queue;
    }

    @Override
    @Schedules({@Scheduled(fixedDelayString = "#{${base.frequency}}"),
            @Scheduled(cron = "${morning.rush}"),
            @Scheduled(cron = "${evening.rush}")})
    public void sendMessage() {
        //TODO: message doorsturen naar Queue
        String message = messageGenerator.generateCameraMessage().toString();
        template.convertAndSend("camera-queue", message);
//        System.out.println(messageGenerator.generateCameraMessage());
    }
}
