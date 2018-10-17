package be.kdg.processor.config;

import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.SpeedingViolation;
import be.kdg.processor.service.proxyservice.ProxyService;
import be.kdg.processor.service.violationservice.EmissionViolationService;
import be.kdg.processor.service.violationservice.SpeedViolationService;
import be.kdg.processor.service.violationservice.ViolationService;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;


@Configuration
public class ViolationConfig {

    @Bean
    public CameraServiceProxy camProxy() {
        return new CameraServiceProxy();
    }

    @Bean
    public LicensePlateServiceProxy lpProxy() {
        return new LicensePlateServiceProxy();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ProxyService proxyService() {
        return new ProxyService(camProxy(), lpProxy(), objectMapper());
    }

    @Bean
    public ArrayList<ViolationService> violations() {
        ArrayList<ViolationService> violationServices = new ArrayList<>();
        violationServices.add(0, new EmissionViolationService(proxyService()));
        violationServices.add(1, new SpeedViolationService(proxyService()));
        return violationServices;
    }
}
