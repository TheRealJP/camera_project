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
    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
    }
}
