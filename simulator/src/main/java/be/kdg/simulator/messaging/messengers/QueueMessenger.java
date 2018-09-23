package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.models.CameraMessage;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
public class QueueMessenger implements Messenger {

    private final RabbitTemplate template;
    private final Queue queue;
    private final MessageGenerator messageGenerator;

    @Autowired
    public QueueMessenger(MessageGenerator messageGenerator, RabbitTemplate template, Queue queue) {
        this.messageGenerator = messageGenerator;
        this.template = template;
        this.queue = queue;
    }

    /**
     * stuurt messages naar de Queue op basis van schedules
     * chron stuurt rate
     *
     */

    @Override
    @Schedules({@Scheduled(fixedDelayString = "#{${base.frequency}}"),
            @Scheduled(cron = "${morning.rush}"),
            @Scheduled(cron = "${evening.rush}")})
    public void sendMessage() {
        try {
            CameraMessage message = messageGenerator.generateCameraMessage();
            template.convertAndSend(queue.getName(), message.toString().getBytes());
            System.out.println("[x] sent: " + message);
        } catch (AmqpException e) {
            e.printStackTrace();
        }

    }
}
