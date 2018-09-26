package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileMessageGenerator implements MessageGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMessageGenerator.class);
    private final ResourceLoader resourceLoader;
    private final ArrayList<CameraMessage> cameraMessages;
    private int fileLineCounter = 0;

    public FileMessageGenerator(ResourceLoader resourceLoader, ArrayList<CameraMessage> cameraMessages) {
        this.resourceLoader = resourceLoader;
        this.cameraMessages = cameraMessages;
        collectMessages();
    }

    @Override
    public CameraMessage generateCameraMessage() {
        if (cameraMessages.size() <= fileLineCounter) return null; //null value will be used to stop service?
        System.out.println(fileLineCounter);
        return cameraMessages.get(fileLineCounter++);
    }

    private void collectMessages() {
        Resource resource = resourceLoader.getResource("files/camera_messages.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");

                System.out.println("printing");
                Thread.sleep(/*Long.parseLong(split[2].trim())*/0); //duurt lang // delay
                cameraMessages.add(new CameraMessage(Integer.parseInt(split[0]), LocalDateTime.now(), split[1]));
            }
        } catch (IOException e) {
            LOGGER.error("IOException occurred", e);
        } catch (InterruptedException e) {
            LOGGER.error("current thread has been interrupted", e);
        }
    }
}
