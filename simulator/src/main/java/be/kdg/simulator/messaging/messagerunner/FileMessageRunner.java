package be.kdg.simulator.messaging.messagerunner;

import be.kdg.simulator.messaging.messengers.QueueMessenger;
import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileMessageRunner implements MessageRunner {

    private final ArrayList<CameraMessage> cameraMessages;
    private final RabbitTemplate template;
    private final Logger log = LoggerFactory.getLogger(QueueMessenger.class);
    private final Queue queue;

    public FileMessageRunner(ArrayList<CameraMessage> cameraMessages, RabbitTemplate template, Queue queue) {
        this.cameraMessages = cameraMessages;
        this.template = template;
        this.queue = queue;
    }

    @Override
    @PostConstruct
    public void messageBuffering() {
        System.out.printf("messages amount %d", cameraMessages.size());
        for (CameraMessage msg : cameraMessages) {

            template.convertAndSend(queue.getName(), msg.toString());
            log.info(msg.toString());
        }
        log.info("reached end of filebuffering");
        System.exit(0); //TODO beter oplossing zoeken, overschakelen op random mode / stoppen?
    }
}
