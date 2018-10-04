package be.kdg.processor;

import be.kdg.sa.services.CameraServiceProxy;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessorApplicationTests {

    @Autowired
    private CameraServiceProxy camProxy;

    @Test
    public void callCameraService()  {
        try {
            assertThat(camProxy.get(1), Matchers.instanceOf(String.class));
            assertThat(camProxy.get(1), Matchers.not(""));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
