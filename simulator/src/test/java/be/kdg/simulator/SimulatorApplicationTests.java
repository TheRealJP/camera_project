package be.kdg.simulator;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.models.CameraMessage;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.util.Assert.isInstanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimulatorApplicationTests {

    @Autowired
    private MessageGenerator messageGenerator;

    @Test
    public void contextLoads() {
        CameraMessage cameraMessage = messageGenerator.generateCameraMessage();

        assertThat(cameraMessage.getLicensePlate(), Matchers.matchesPattern("^1-\\w{3}-\\d{3}$"));
        assertThat(cameraMessage, Matchers.notNullValue());
        isInstanceOf(LocalDateTime.class, cameraMessage.getDateTime(), "Its not a date!");
        isInstanceOf(Integer.class, cameraMessage.getId(), "Its not a number!");
    }
}
