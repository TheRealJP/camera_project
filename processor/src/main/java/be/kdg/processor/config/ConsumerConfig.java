package be.kdg.processor.config;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.service.ViolationService;
import be.kdg.processor.service.ViolationServiceImplementation;
import be.kdg.processor.service.listeners.MessageBuffer;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ConsumerConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ArrayList<CameraMessage> cameraMessages() {
        return new ArrayList<>();
    }

    @Bean
    public CameraServiceProxy camProxy() {
        return new CameraServiceProxy();
    }

    @Bean
    public LicensePlateServiceProxy licensePlateServiceProxy() {
        return new LicensePlateServiceProxy();
    }

    @Bean
    public ViolationService violationService() {
        return new ViolationServiceImplementation(camProxy(), licensePlateServiceProxy(), objectMapper(), messageBuffer());
    }

    @Bean
    public MessageBuffer messageBuffer() {
        return new MessageBuffer(cameraMessages());
    }


}
