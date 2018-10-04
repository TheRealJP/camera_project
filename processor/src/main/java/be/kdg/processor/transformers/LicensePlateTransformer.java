package be.kdg.processor.transformers;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "transformer.type", havingValue = "lp")
public class LicensePlateTransformer implements MessageTransformer {

    @Override
    public String transformToObject(String msg) {
        String[] split = msg.split(",");
        return split[3]; //licenseplate
    }
}
