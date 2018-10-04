package be.kdg.processor.config;

import be.kdg.processor.models.CameraMessage;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ConsumerConfig {

    @Bean
    public ArrayList<CameraMessage> cameraMessages() {
        return new ArrayList<>();
    }

    @Bean
    public ArrayList<String> licensePlates() {
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


}
