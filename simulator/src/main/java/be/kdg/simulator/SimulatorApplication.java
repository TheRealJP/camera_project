package be.kdg.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.*;
import java.nio.file.Files;

//    @Autowired // field injection zorgt ervoor dat we cyclical dependencies kunnen vermijden indien dit voorkomt
//    private MessageGenerator generator;
//    /*@Component boven een afgeleide van deze interface zorgt ervoor dat deze kan geinject worden in deze constructor*/
//    public SimulatorApplication(MessageGenerator messageGenerator) {
//        this.generator = messageGenerator;
//    }

// container wordt hier gebouwd bij run()
// deze klasse is ook gewoon een bean
@SpringBootApplication
@EnableScheduling //moved this here from config class
public class SimulatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
    }
}
