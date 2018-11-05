package be.kdg.processor.cameramessage.service.consumers;

import be.kdg.processor.cameramessage.config.RabbitConfig;
import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.cameramessage.repositories.CameraMessageRepository;
import be.kdg.processor.cameramessage.service.transformers.MessageTransformer;
import be.kdg.processor.observer.publishers.MessagePublisher;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.InvalidLicensePlateException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * transforms incoming xml to cameramessage object
 * publishes message to listeners
 * retries to consume message depending on the settings in the RetryableConfig class
 */

@Component
@RabbitListener(queues = RabbitConfig.MESSAGE_QUEUE)
public class QueueConsumer implements Consumer {
    private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);
    private final MessageTransformer transformer;
    private final MessagePublisher messagePublisher;
    private final CameraMessageRepository cmr;
    private final RabbitTemplate rabbitTemplate;
    private final RetryTemplate retryTemplate;

    public QueueConsumer(MessageTransformer transformer, MessagePublisher messagePublisher, CameraMessageRepository cmr, RabbitTemplate rabbitTemplate, RetryTemplate retryTemplate) {
        this.transformer = transformer;
        this.messagePublisher = messagePublisher;
        this.cmr = cmr;
        this.rabbitTemplate = rabbitTemplate;
        this.retryTemplate = retryTemplate;
    }

    @RabbitHandler
    public void consume(final String in) throws IOException, InvalidLicensePlateException, LicensePlateNotFoundException, CameraNotFoundException {
        retryTemplate.execute(context -> {
            CameraMessage cm = (CameraMessage) transformer.transformMessage(in);
            cmr.save(cm);
            messagePublisher.publishMessage(cm);
            return null;

        }, retryCallBack -> {
            recover(retryCallBack, in);
            return null;

        });
    }

    private void recover(RetryContext retryContext, String in) {
        log.error("Error: {} - CameraMessage {} - Placing on ErrorQueue.", retryContext.getLastThrowable().getMessage(), in);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_ERROR_KEY, in);
    }
}
