package be.kdg.processor.violation.service;

import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.cameramessage.repositories.CameraMessageRepository;
import be.kdg.processor.observer.events.ConsumeEvent;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.processor.proxy.service.ProxyService;
import be.kdg.processor.violation.models.SpeedingViolation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpeedViolationServiceTest {
    @Autowired
    private CameraMessageRepository cameraMessageRepository;
    @Autowired
    private SpeedViolationService speedViolationService;
    @Autowired
    private ProxyService proxyService;


    @Test
    public void checkViolation() throws IOException {
        //first observation for a given licenseplate
        CameraMessage firstMessage = new CameraMessage(1, LocalDateTime.of(LocalDate.of(2018, 11, 6), LocalTime.of(21, 45, 50, 55588000)), "4-ABC-123");
        cameraMessageRepository.save(firstMessage);

        //second observation for a given licenseplate
        Camera otherCamera = proxyService.collectCamera(2);
        LicensePlate lp = proxyService.collectLicensePlate("4-ABC-123");
        CameraMessage triggerMessage = new CameraMessage(2, LocalDateTime.of(LocalDate.of(2018, 11, 6), LocalTime.of(21, 46, 9, 55588000)), "4-ABC-123");
        ConsumeEvent secondEvent = new ConsumeEvent(this, triggerMessage, otherCamera, null, lp);

        //violation check
        SpeedingViolation speedingViolation = speedViolationService.checkViolation(secondEvent);

        //test
        assertNotNull(speedingViolation);
        assertThat(speedingViolation.getSpeed() > speedingViolation.getSpeedLimit(), is(true));
    }

    @Test
    public void calculateSpeed() {
        CameraMessage cm1 = new CameraMessage(1, LocalDateTime.of(LocalDate.of(2018, 11, 6), LocalTime.of(21, 45, 50, 55588000)), "4-ABC-123");
        CameraMessage cm2 = new CameraMessage(2, LocalDateTime.of(LocalDate.of(2018, 11, 6), LocalTime.of(21, 46, 9, 55588000)), "4-ABC-123");
        int i = speedViolationService.calculateSpeed(1000, cm1, cm2);

        assertThat(i <= 0, is(false));
    }
}