package be.kdg.processor.service.violationcontrol;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.violations.EmissionViolation;
import be.kdg.processor.models.violations.SpeedingViolation;
import be.kdg.processor.models.violations.Violation;
import be.kdg.processor.service.listeners.MessageBuffer;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;

/**
 * snelheid berekenen
 * snelheid & emissie checken
 */


public class ViolationServiceImplementation implements ViolationService {

    private final CameraServiceProxy camProxy;
    private final LicensePlateServiceProxy lpProxy;
    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(ViolationServiceImplementation.class);
    private final MessageBuffer messageBuffer;
    private final FineService fineService;

    @Value("${timeframe.emission}")
    private int timeframe;
    private ArrayList<Violation> messageViolations;
    private ArrayList<CameraMessage> cameraMessages;

    public ViolationServiceImplementation(CameraServiceProxy camProxy, LicensePlateServiceProxy lpProxy, ObjectMapper objectMapper, MessageBuffer messageBuffer, FineService fineService) {
        this.camProxy = camProxy;
        this.lpProxy = lpProxy;

        this.objectMapper = objectMapper;
        this.messageBuffer = messageBuffer;
        this.fineService = fineService;

        this.messageViolations = new ArrayList<>();
        this.cameraMessages = messageBuffer.getCameraMessages();
    }

    //TODO: later this wel insert a new violation in its table
    @Override
    public void checkViolation() {
        try {
            checkEmission();
            checkSpeed();
        } catch (IOException | LicensePlateNotFoundException | CameraNotFoundException | NullPointerException e) {
            log.error(e.getMessage());
        }
    }

    private void checkEmission() throws IOException {
        //DATABANK: get camaramessages van de laatste 30 minuten WHERE timestamp > timestamp - timeframe
        // TODO: checken op nummerplaat in de gequery'de messages of deze recent al een boete heeft gekregen en of deze binnen dat timeframe valt

        for (CameraMessage cm : cameraMessages) {
            Camera cam = collectCamera(cm.getId());
            LicensePlate lp = collectLicensePlate(cm.getLicensePlate());

            if (cam.getEuroNorm() > lp.getEuroNumber()) {
                EmissionViolation emissionViolation = new EmissionViolation(cam.getEuroNorm(), lp.getEuroNumber(), lp.getPlateId());
                if (!messageViolations.contains(emissionViolation) /*&& timeframe < 100000*/) { //TODO: calculate interval between + 1 day and cameramessage?
                    log.info(String.format("Licenseplate %s will receive a emission fine. cameraNorm=%d, carNorm=%d)", lp.getPlateId(), cam.getEuroNorm(), lp.getEuroNumber()));
                    messageViolations.add(emissionViolation);
                    log.info(String.valueOf(messageViolations.size()));
                }
            }
        }
    }

    /**
     * loopen over de verzamelde cameraberichten gedurende de @timeframe (bv 30 min)
     * berichten scannen en verwijderen als deze ouder zijn dan bv een minuut
     */
    private void checkSpeed() throws IOException {
        for (CameraMessage cm : cameraMessages) {
            Camera cam = collectCamera(cm.getId());
            LicensePlate lp = collectLicensePlate(cm.getLicensePlate());

            if (cam.getSegment() != null) {
                Camera otherCamera = collectCamera(cam.getSegment().getConnectedCameraId());
                // for each message, check if theres another message with the other camera id and its about the same licenseplate
                for (CameraMessage cm2 : cameraMessages) {
                    boolean sameLicensePlate = cm.getLicensePlate().equals(cm2.getLicensePlate());
                    boolean otherCameraFound = otherCamera.getCameraId() == cm2.getId();

                    if (otherCameraFound && sameLicensePlate) { //check if the message is about the same licenseplate AND the same other camera
                        int speed = calculateSpeed(cam.getSegment().getDistance(), cm, cm2);
                        int speedLimit = cam.getSegment().getSpeedLimit();
                        if (speed > speedLimit) {
                            SpeedingViolation speedingViolation = new SpeedingViolation(speed, lp, cam, otherCamera);
                            // TODO: checken op nummerplaat in de gequery'de messages of deze recent al een boete heeft gekregen en of deze binnen dat timeframe valt
                            if (!messageViolations.contains(speedingViolation) /*&& timeframe < 100000*/) {
                                messageViolations.add(new SpeedingViolation(speed, lp, cam, otherCamera));
                                log.info(String.format("Licenseplate %s will receive a speeding fine. speed=%d, carNorm=%d)", lp.getPlateId(), speed, speedLimit));
                                log.info(String.valueOf(messageViolations.size()));
                            }
                        }
                    }
                }
            }
        }
    }

    private int calculateSpeed(int distance, CameraMessage firstMessage, CameraMessage secondMessage) {
        int firstMessageTime = firstMessage.getDateTime().toLocalTime().toSecondOfDay();
        int secondMessageTime = secondMessage.getDateTime().toLocalTime().toSecondOfDay();
        int time = secondMessageTime - firstMessageTime;
        return distance / time;
    }

    @Override
    public Camera collectCamera(int camId) throws IOException {
        String camJson = camProxy.get(camId);
        return objectMapper.readValue(camJson, Camera.class);
    }

    @Override
    public LicensePlate collectLicensePlate(String lp) throws IOException {
        String lpJson = lpProxy.get(lp);
        return objectMapper.readValue(lpJson, LicensePlate.class);
    }

}
