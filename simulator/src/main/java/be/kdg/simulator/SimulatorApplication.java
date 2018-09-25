package be.kdg.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


// container wordt hier gebouwd bij run?
// dit is ook gewoon een bean
@SpringBootApplication
@EnableScheduling
public class SimulatorApplication {

//    @Autowired // field injection zorgt ervoor dat we cyclical dependencies kunnen vermijden indien dit voorkomt
//    private MessageGenerator generator;

//    /*@Component boven een afgeleide van deze interface zorgt ervoor dat deze kan geinject worden in deze constructor*/
//    public SimulatorApplication(MessageGenerator messageGenerator) {
//        this.generator = messageGenerator;
//    }

    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
    }
}
