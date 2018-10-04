package be.kdg.simulator.messaging.messagerunner;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;


@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "random")
public class RandomMessageRunner implements MessageRunner {

    private final MessageGenerator messageGenerator;
    private final Queue queue;
    private final RabbitTemplate template;
    private final Logger log = LoggerFactory.getLogger(RandomMessageRunner.class);
    /*traffic intensity*/
    @Value("#{${default.traffic}}")
    private int defaultInterval;
    @Value("#{${busy.traffic}}")
    private int busyInterval;

    public RandomMessageRunner(MessageGenerator messageGenerator, Queue queue, RabbitTemplate template) {
        this.messageGenerator = messageGenerator;
        this.template = template;
        this.queue = queue;
    }

    @Override
    @PostConstruct
    public void messageBuffering() {
        while (true) {
            try {
                /* depending on moment of the day -> longer/shorter interval */
                int hour = LocalDateTime.now().getHour();
                if (hour > 7 && hour <= 9 || hour > 17 && hour <= 20) Thread.sleep(busyInterval);
                else Thread.sleep(defaultInterval);

                /* logging + sending message to queue */
                CameraMessage msg = messageGenerator.generateCameraMessage();

//                https://docs.spring.io/spring-amqp/reference/htmlsingle/#java-deserialization
                Message message = MessageBuilder.withBody(msg.toString().getBytes())
                        .setContentType(MessageProperties.CONTENT_TYPE_XML)
                        .build();

                template.convertAndSend(queue.getName(), msg.toString());
                log.info(msg.toString());
            } catch (InterruptedException e) {
                log.error(e.toString());
            }
        }
    }
}
