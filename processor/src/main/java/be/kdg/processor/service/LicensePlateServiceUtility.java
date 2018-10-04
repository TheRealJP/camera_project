package be.kdg.processor.service;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.transformers.MessageTransformer;
import be.kdg.sa.services.LicensePlateServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class LicensePlateServiceUtility {

    private final LicensePlateServiceProxy licensePlateServiceProxy;
    private final Logger log = LoggerFactory.getLogger(CameraServiceUtility.class);
    private final ArrayList<CameraMessage> cameraMessages;
    private final ArrayList<String> licensePlates;
    private final MessageTransformer messageTransformer;

    public LicensePlateServiceUtility(LicensePlateServiceProxy licensePlateServiceProxy, ArrayList<CameraMessage> cameraMessages, ArrayList<String> licensePlates, MessageTransformer messageTransformer) {
        this.licensePlateServiceProxy = licensePlateServiceProxy;
        this.cameraMessages = cameraMessages;
        this.licensePlates = licensePlates;
        this.messageTransformer = messageTransformer;
    }

    @PostConstruct
    public void checkLicensePlate() {
        try {
            System.out.println("reached checkLicensePlate");
            for (String l : licensePlates) {
                System.out.println("reached checkLicensePlate");
                log.info(licensePlateServiceProxy.get(l));
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
    }
}
