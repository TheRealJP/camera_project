package be.kdg.simulator.messaging.messagerunner;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.models.CameraMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
import java.time.format.DateTimeFormatter;


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

            XmlMapper mapper = new XmlMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
            mapper.registerModule(javaTimeModule);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            try {
                /* depending on moment of the day -> longer/shorter interval */
                int hour = LocalDateTime.now().getHour();
                if (hour > 7 && hour <= 9 || hour > 17 && hour <= 20) Thread.sleep(busyInterval);
                else Thread.sleep(defaultInterval);

                CameraMessage msg = messageGenerator.generateCameraMessage();
                String cameraMessageXml = mapper.writeValueAsString(msg);
                System.out.println(cameraMessageXml);
                template.convertAndSend(queue.getName(), cameraMessageXml);

                log.info(msg.toString());
            } catch (InterruptedException e) {
                log.error(e.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
