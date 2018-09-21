package be.kdg.simulator.messaging.messengers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class) //applicatiecontext gestart
@SpringBootTest
public class CommandLineMessengerTest {

    @Autowired //field injection
    private Messenger messenger;

    @Test //Spring applicatie == Spring container --> geen container == geen beans
    public void sendMessage() {
        messenger.sendMessage();
    }
}