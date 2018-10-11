package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

@Component //maakt van RandomMessageGenerator een bean
/*name = verzonnen --> check bij application.properties file*/
@ConditionalOnProperty(name = "generator.type", havingValue = "random")
// this annotation adds this bean to spring container
public class RandomMessageGenerator implements MessageGenerator {

    /*traffic intensity*/
    @Value("#{${default.traffic}}")
    private int defaultInterval;
    @Value("#{${busy.traffic}}")
    private int busyInterval;

    private static final Logger log = LoggerFactory.getLogger(RandomMessageGenerator.class);
    @Value("#{${camera.amount}}")
    private int maxId;
    private Random r;

    public RandomMessageGenerator() {
        this.r = new Random();
//        fillLicensePlateCollection();
    }

    /**
     * ISSUES
     * 1) kleine pool van licenseplates, kans op registratie door 2 verschillende cameras
     * op 2 dezelfde tijdstippen van dezelfde nummerplaat
     * gevolg: bij boeteberekening --> bv: auto met nummerplaat rijd dan 500km/uur in de bebouwde kom
     * licensePlates.get(r.nextInt(licensePlates.size())));
     * 2) 2^6 aantal platen, weinig kans op een snelheidsberekening
     */
    @Override
    public CameraMessage generateCameraMessage() {
        trafficSimulator();
        return new CameraMessage(r.nextInt(maxId) + 1, LocalDateTime.now(), randomLicensePlateGenerator());
    }

    /*implies traffic intensity by putting a delay between each message*/
    private void trafficSimulator() {
        try {
            int hour = LocalDateTime.now().getHour();
            if (hour > 7 && hour <= 9 || hour > 17 && hour <= 20) Thread.sleep(busyInterval);
            else Thread.sleep(defaultInterval);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
    
    private String randomLicensePlateGenerator() {
        StringBuilder licensePlate = new StringBuilder("1-");
        StringBuilder textPart = new StringBuilder();
        StringBuilder numberPart = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            char randomUpperCase = (char) ('A' + r.nextInt(26));
            textPart.append(randomUpperCase);
            numberPart.append(r.nextInt(9) + 1);
        }
        licensePlate.append(textPart).append("-").append(numberPart);
        return licensePlate.toString();
    }
}
