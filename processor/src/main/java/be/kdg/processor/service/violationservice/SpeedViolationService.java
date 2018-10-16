package be.kdg.processor.service.violationservice;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.violations.SpeedingViolation;
import be.kdg.processor.service.proxyservice.ProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public class SpeedViolationService implements ViolationService {

    private final Logger log = LoggerFactory.getLogger(SpeedViolationService.class);
    private final ProxyService proxyService;

    public SpeedViolationService(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    /**
     * loopen over de verzamelde cameraberichten gedurende de @timeframe (bv 30 min)
     * berichten scannen en verwijderen als deze ouder zijn dan bv een minuut
     *
     * @param cameraMessages
     */

    @Override
    public SpeedingViolation checkViolation(Collection<CameraMessage> cameraMessages) throws IOException {
        for (CameraMessage cm : cameraMessages) {

            Camera cam = proxyService.collectCamera(cm.getCameraId());
            LicensePlate lp = proxyService.collectLicensePlate(cm.getLicensePlate());

            if (cam.getSegment() != null) {
                Camera otherCamera = proxyService.collectCamera(cam.getSegment().getConnectedCameraId());
                // for each message, check if theres another message with the other camera cameraId and its about the same licenseplate
                for (CameraMessage cm2 : cameraMessages) {
                    boolean sameLicensePlate = cm.getLicensePlate().equals(cm2.getLicensePlate());
                    boolean otherCameraFound = otherCamera.getCameraId() == cm2.getCameraId();

                    if (otherCameraFound && sameLicensePlate) { //check if the message is about the same licenseplate AND the same other camera
                        int speed = calculateSpeed(cam.getSegment().getDistance(), cm, cm2);
                        int speedLimit = cam.getSegment().getSpeedLimit();
                        if (speed > speedLimit) { // TODO: checken op nummerplaat in de gequery'de messages of deze recent al een boete heeft gekregen en of deze binnen dat timeframe valt
                            log.info(String.format("Licenseplate %s will receive a speeding fine. speedlimit= %d, speed= %d)", lp.getPlateId(), speedLimit, speed));
                            return new SpeedingViolation(speed, lp, cam, otherCamera);
                        }
                    }
                }
            }
        }
        return null;
    }

    private int calculateSpeed(int distance, CameraMessage firstMessage, CameraMessage secondMessage) {
        int firstMessageTime = firstMessage.getDateTime().toLocalTime().toSecondOfDay();
        int secondMessageTime = secondMessage.getDateTime().toLocalTime().toSecondOfDay();
        int time = secondMessageTime - firstMessageTime;
        return distance / time;
    }
}
