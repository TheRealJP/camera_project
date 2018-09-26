package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
public class QueueMessenger implements Messenger {

    private final MessageGenerator messageGenerator;
    private final RabbitTemplate template;
    private final Queue queue;
    private final Logger log = LoggerFactory.getLogger(QueueMessenger.class);

    public QueueMessenger(MessageGenerator messageGenerator, RabbitTemplate template, Queue queue) {
        this.messageGenerator = messageGenerator;
        this.template = template;
        this.queue = queue;
    }

    /**
     * #  Doel: scheduling op default laten draaien als filemode aan staat
     * 1. zorgen voor een hele lange delay, 1 methode (-)
     * 2. sendmessage delegeren, nieuwe methodes van eigen scheduling voorzien (+)
     */
    @Bean
    @Schedules({@Scheduled(fixedDelayString = "#{${base.frequency}}"),
            @Scheduled(cron = "${morning.rush}"),
            @Scheduled(cron = "${evening.rush}")})
    @ConditionalOnProperty(name = "generator.type", havingValue = "random")
    public void sendRandomMessages() {
        sendMessage();
    }

    @Scheduled(fixedDelay = 1000L)
    @ConditionalOnProperty(name = "generator.type", havingValue = "file")
    public void sendFileMessages() {
        sendMessage();
    }

    @Override
    public void sendMessage() {
        String message = messageGenerator.generateCameraMessage().toString();
        if (!message.isEmpty()) {
//            template.convertAndSend("camera-queue", message);
            log.info(message);
        }
    }
}
