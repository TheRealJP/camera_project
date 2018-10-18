package be.kdg.processor.service.violationservice;

import be.kdg.processor.models.fine.Fine;
import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.Violation;
import be.kdg.processor.service.fineservice.FineService;
import be.kdg.processor.service.transformers.MessageTransformer;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmissionViolationServiceTest {

    @Autowired
    private EmissionViolationService emissionVS;
    @Autowired
    private MessageTransformer messageTransformer;
    @Autowired
    private FineService fineService;


    @Test
    public void checkViolation() throws IOException {


        ArrayList<CameraMessage> messages = new ArrayList<>();
        messages.add(new CameraMessage(4, LocalDateTime.now(), "2-ABC-123"));
        messages.add(new CameraMessage(5, LocalDateTime.now(), "2-ABC-123"));
        messages.add(new CameraMessage(5, LocalDateTime.now(), "1-ABC-123"));

        for (CameraMessage cm : messages) {
            Violation v = emissionVS.checkViolation(cm, messages);
            Fine fine = fineService.createAndSaveFine(v);
            assertThat(v, Matchers.instanceOf(EmissionViolation.class));
            assertThat(fine, Matchers.notNullValue(Fine.class));
        }

    }


}