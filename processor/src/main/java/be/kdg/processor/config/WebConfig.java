package be.kdg.processor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc <---------- niet meer gebruiken
public class WebConfig implements WebMvcConfigurer { //sinds java8 dit gebruiken

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("start"); //als je naar root surft ga dan naar start , spring plakt er nog .html achter
    }


}
