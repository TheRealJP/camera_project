package be.kdg.simulator;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.messaging.messengers.Messenger;
import be.kdg.simulator.simulator.Simulator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


// container wordt hier gebouwd bij run?
// dit is ook gewoon een bean
@SpringBootApplication
public class SimulatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
        Simulator.runSimulator();
    }
}