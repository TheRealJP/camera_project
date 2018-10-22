package be.kdg.processor.fine.config;

import be.kdg.processor.fine.repositories.FineFactorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc <---------- niet meer gebruiken
public class FineWebConfig implements WebMvcConfigurer { //sinds java8 dit gebruiken

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("start"); //als je naar root surft ga dan naar start , spring plakt er nog .html achter
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
