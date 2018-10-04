package be.kdg.processor.transformers;


import be.kdg.processor.models.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CameraMessageTransformer implements MessageTransformer {

    private static final Logger log = LoggerFactory.getLogger(CameraMessageTransformer.class);

    @Override
    public CameraMessage transformToCameraMessage(String msg) {
        String[] split = msg.split(",");
        System.out.println(split[0] + split[1] + split[2] + split[3]);
        
        return null;
    }
}
