package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileMessageGenerator implements MessageGenerator {

    private static final Logger log = LoggerFactory.getLogger(FileMessageGenerator.class);
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
        return cameraMessages.get(fileLineCounter++);
    }

    private void collectMessages() {
        Resource resource = resourceLoader.getResource("files/camera_messages.csv"); //${file.path} werkt niet
        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {

            LocalDateTime currentTime = LocalDateTime.now();
            String line = "";

            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                currentTime = currentTime.plus(Long.parseLong(split[2].trim()), ChronoField.MILLI_OF_DAY.getBaseUnit());
                cameraMessages.add(new CameraMessage(Integer.parseInt(split[0]), currentTime, split[1]));
            }
        } catch (IOException e) {
            log.error("IOException occurred", e);
        }
    }
}
