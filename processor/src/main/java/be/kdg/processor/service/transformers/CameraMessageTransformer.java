package be.kdg.processor.service.transformers;


import be.kdg.processor.models.messages.CameraMessage;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//https://docs.spring.io/spring-amqp/reference/htmlsingle/#message-builder
@Component
@ConditionalOnProperty(name = "transformer.type", havingValue = "xml")
public class CameraMessageTransformer implements MessageTransformer {

    private final Logger log = LoggerFactory.getLogger(CameraMessageTransformer.class);

    @Override
    public CameraMessage transformMessage(String msg) {
        try {
            XmlMapper mapper = new XmlMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
            mapper.registerModule(javaTimeModule);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            return mapper.readValue(msg, CameraMessage.class);

        } catch (IOException e) {
            log.warn(e.toString());
        }

        return null;
    }
}
