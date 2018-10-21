package be.kdg.processor.violation.config;

import be.kdg.processor.proxy.service.ProxyService;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
}
