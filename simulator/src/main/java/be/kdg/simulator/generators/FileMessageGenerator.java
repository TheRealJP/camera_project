package be.kdg.simulator.generators;

import be.kdg.simulator.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileMessageGenerator implements MessageGenerator {

    private static final Logger log = LoggerFactory.getLogger(FileMessageGenerator.class);
    private final ResourceLoader resourceLoader;
    @Value("${file.path}")
    private String filepath;
    private int fileLineCounter = 0;

    public FileMessageGenerator(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public CameraMessage generateCameraMessage() {
        return collectMessage();
    }

    /**
     * Consumes file line by line instead of saving it into memory.
     * Each line is transformed to a cameramessage and sent off to a messenger.
     */
    private CameraMessage collectMessage() {
        Resource resource = resourceLoader.getResource(filepath); //${file.path} werkt niet

        Supplier<Stream<String>> lines = () -> {
            try {
                return lines(Paths.get(resource.getFile().getPath()));
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
            return null;
        };

        if (fileLineCounter >= lines.get().count()) return null;

        String line = lines.get().skip(fileLineCounter++).findFirst().get();
        LocalDateTime currentTime = LocalDateTime.now();
        String[] split = line.split(",");
        currentTime = currentTime.plus(Long.parseLong(split[2].trim()), ChronoField.MILLI_OF_DAY.getBaseUnit());

        return new CameraMessage(Integer.parseInt(split[0]), currentTime, split[1]);
    }
}