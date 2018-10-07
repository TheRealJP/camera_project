package be.kdg.processor.config;

import be.kdg.processor.models.violations.EmissionViolation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ObserverConfig {
    @Bean
    public ArrayList<EmissionViolation> emissionViolations(){
        return new ArrayList<>();
    }
}
