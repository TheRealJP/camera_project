package be.kdg.simulator.messaging.messagerunner;

import be.kdg.simulator.messaging.messengers.QueueMessenger;
import be.kdg.simulator.models.CameraMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        //TODO: klasse schrijven voor te transformeren naar xml
        XmlMapper mapper = new XmlMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        mapper.registerModule(javaTimeModule);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        System.out.printf("messages amount %d", cameraMessages.size());
        for (CameraMessage msg : cameraMessages) {
            try {
                String cameraMessageXml = mapper.writeValueAsString(msg);
                System.out.println(cameraMessageXml);
                template.convertAndSend(queue.getName(), cameraMessageXml);
//                template.convertAndSend(queue.getName(), msg.toString());
                log.info(cameraMessageXml);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        log.info("reached end of filebuffering");
        System.exit(0); //TODO beter oplossing zoeken, overschakelen op random mode / stoppen?
    }
}
