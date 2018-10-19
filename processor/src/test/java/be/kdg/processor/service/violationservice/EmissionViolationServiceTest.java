package be.kdg.processor.service.violationservice;

import be.kdg.processor.models.fine.Fine;
import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.Violation;
import be.kdg.processor.service.fineservice.FineService;
import be.kdg.processor.service.proxyservice.ProxyService;
import be.kdg.processor.service.transformers.MessageTransformer;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmissionViolationServiceTest {

    @Autowired
    private EmissionViolationService emissionVS;
    @Autowired
    private MessageTransformer messageTransformer;
    @Autowired
    private FineService fineService;
    @Autowired
    private EmissionViolationService emissionViolationService;
    @Autowired
    private ProxyService proxyService;

    @Test
    public void checkViolation() throws IOException {
        ArrayList<CameraMessage> messages = new ArrayList<>();
        messages.add(new CameraMessage(4, LocalDateTime.now(), "2-ABC-123"));
        messages.add(new CameraMessage(5, LocalDateTime.now(), "2-ABC-123"));
        messages.add(new CameraMessage(5, LocalDateTime.now(), "1-ABC-123"));

        CameraMessage cameraMessage = new CameraMessage();
        cameraMessage.setCameraId(1);
        cameraMessage.setLicensePlate("1-GOD-888");

        Camera camera = new Camera();
        camera.setCameraId(1);
        camera.setEuroNorm(3);

        LicensePlate licensePlate = new LicensePlate();
        licensePlate.setEuroNumber(2);
        licensePlate.setPlateId("1-GOD-888");
        licensePlate.setNationalNumber("HALLELUJA ZIMBABWE");

        given(proxyService.collectCamera(any(Integer.class))).willReturn(camera);
        given(proxyService.collectLicensePlate(any(String.class))).willReturn(licensePlate);

        Violation v = emissionViolationService.checkViolation(cameraMessage, messages);
        assertThat(v, Matchers.instanceOf(EmissionViolation.class));

        verify(proxyService, times(1)).collectCamera(1);
        verify(proxyService, times(1)).collectLicensePlate("1-GOD-888");
//        Violation violation = emissionViolationService.checkViolation(messages.get(0), messages);
//        assertNotNull(violation);
//        EmissionViolation emissionViolation = new EmissionViolation(camera, );
//        given(emissionViolationService.checkViolation(messages.get(0), messages)).willReturn();

//        assertThat(fine, Matchers.notNullValue(Fine.class));
    }
}