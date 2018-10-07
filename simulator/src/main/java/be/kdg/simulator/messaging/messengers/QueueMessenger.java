package be.kdg.simulator.messaging.messengers;

import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.transformers.MessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
public class QueueMessenger implements Messenger {

    private final MessageTransformer transformer;
    private final RabbitTemplate template;
    private final Queue queue;
    private final Logger log = LoggerFactory.getLogger(QueueMessenger.class);

    public QueueMessenger(MessageTransformer transformer, RabbitTemplate template, Queue queue) {
        this.transformer = transformer;
        this.template = template;
        this.queue = queue;
    }

    @Override
    public void sendMessage(CameraMessage message) {
        try {
            String parsedCameraMessage = (String) transformer.transformMessage(message);
            template.convertAndSend(queue.getName(), parsedCameraMessage);
            log.info('\n' + parsedCameraMessage);
        } catch (AmqpException e) {
            log.error(e.getMessage());
        }
    }
}
