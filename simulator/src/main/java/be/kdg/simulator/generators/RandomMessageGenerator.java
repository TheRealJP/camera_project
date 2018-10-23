package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator {

    private static final Logger log = LoggerFactory.getLogger(RandomMessageGenerator.class);
    /*traffic intensity*/
    @Value("#{${default.traffic}}")
    private int defaultInterval;
    @Value("#{${busy.traffic}}")
    private int busyInterval;
    @Value("#{${camera.amount}}")
    private int maxId;
    private Random r;

    public RandomMessageGenerator() {
        this.r = new Random();
    }

    @Override
    public CameraMessage generateCameraMessage() throws InterruptedException {
        trafficSimulator();
        return new CameraMessage(r.nextInt(maxId) + 1, LocalDateTime.now(), randomLicensePlateGenerator());
    }

    /**
     * implies traffic intensity by putting a delay between each message
     */
    private void trafficSimulator() throws InterruptedException {
        int hour = LocalDateTime.now().getHour();
        if (hour > 7 && hour <= 9 || hour > 17 && hour <= 20) Thread.sleep(busyInterval);
        else Thread.sleep(defaultInterval);
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
