package be.kdg.processor.config;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.repositories.FineRepository;
import be.kdg.processor.service.listeners.MessageBuffer;
import be.kdg.processor.service.violationcontrol.FineService;
import be.kdg.processor.service.violationcontrol.ViolationService;
import be.kdg.processor.service.violationcontrol.ViolationServiceImplementation;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
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
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public MessageBuffer messageBuffer() {
        return new MessageBuffer(cameraMessages());
    }


}
