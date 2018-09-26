package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileMessageGenerator implements MessageGenerator {

    private ResourceLoader resourceLoader;
    private ArrayList<String[]> fileLines;
    private final ArrayList<CameraMessage> cameraMessages;

    public FileMessageGenerator(ResourceLoader resourceLoader, ArrayList<CameraMessage> cameraMessages) {
        this.resourceLoader = resourceLoader;
        this.cameraMessages = cameraMessages;
        fileLines = new ArrayList<>();
        collectMessages();

    }

    @Override
    public CameraMessage generateCameraMessage() {
        //loopen list adhv size, 1 voor 1 naar Q sturen
        //
        return new CameraMessage(2, LocalDateTime.now(), "1-XYZ-987");
    }

    //TODO: read txt and convert to message objects

    //    @Bean
    // file -->
//    public ArrayList<String[]> collectMessages() {
    private void collectMessages() {
        Resource resource = resourceLoader.getResource("files/camera_messages.txt");
        fileLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                fileLines.add(split);

                Thread.sleep(Integer.parseInt(split[2]));
                cameraMessages.add(new CameraMessage(Integer.parseInt(split[0]), LocalDateTime.now(), split[1]));
            }
        } catch (IOException e) {
            //TODO: add logger
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        return fileLines;
    }
}
