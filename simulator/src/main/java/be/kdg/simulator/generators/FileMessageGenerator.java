package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileMessageGenerator implements MessageGenerator {

    private ArrayList<String[]> fileLines;
    private int count = fileLines.size();

    public FileMessageGenerator() {
        fileLines = readFileCollectLines();
    }

    @Override
    public CameraMessage generateCameraMessage() {
        String[] line = fileLines.get(count--);
        return new CameraMessage(Integer.parseInt(line[0]), LocalDateTime.now(), line[2]);
    }

    @Bean
    private ArrayList<String[]> readFileCollectLines() {
        Resource resource = new ClassPathResource("files/camera_messages.txt");
//        ArrayList<CameraMessage> camMsgs = new ArrayList<>();
        ArrayList<String[]> splitLines = new ArrayList<>();
        String[] split = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) break;
                split = line.split(",");
                splitLines.add(split);
                System.out.println(Arrays.asList(split));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return splitLines;
    }
}
