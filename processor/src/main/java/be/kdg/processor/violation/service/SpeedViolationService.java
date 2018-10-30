package be.kdg.processor.violation.service;

import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.cameramessage.repositories.CameraMessageRepository;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.processor.proxy.models.Segment;
import be.kdg.processor.proxy.service.ProxyService;
import be.kdg.processor.violation.models.SpeedingViolation;
import be.kdg.processor.violation.observerpattern.events.ConsumeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

@Service
public class SpeedViolationService implements ViolationService {

    private final Logger log = LoggerFactory.getLogger(SpeedViolationService.class);
    private final ProxyService proxyService;
    private final CameraMessageRepository cmr;
    @Value("#{${timeframe.between.cameras}}")
    private long timestamp;


    public SpeedViolationService(ProxyService proxyService, CameraMessageRepository cmr) {
        this.proxyService = proxyService;
        this.cmr = cmr;
    }

    @Override
    public SpeedingViolation checkViolation(ConsumeEvent event) throws IOException, ArithmeticException {

        Camera cam = event.getCamera();
        LicensePlate lp = event.getLp();
        CameraMessage cm = event.getCameraMessage();

        if (cam.getSegment() != null) {
            Camera otherCamera = proxyService.collectCamera(cam.getSegment().getConnectedCameraId());
            LocalDateTime bufferTimeFrame = LocalDateTime.now().minus(timestamp, ChronoField.MILLI_OF_DAY.getBaseUnit()); //timestamp is after 30 minutes ago
            List<CameraMessage> cameraMessages = cmr.findAllByDateTimeIsAfter(bufferTimeFrame);

            for (CameraMessage cm2 : cameraMessages) {
                boolean sameLicensePlate = cm.getLicensePlate().equals(cm2.getLicensePlate());
                boolean otherCameraFound = otherCamera.getCameraId() == cm2.getCameraId();

                if (otherCameraFound && sameLicensePlate) {
                    Segment segment = cam.getSegment();
                    int speed = calculateSpeed(cam.getSegment().getDistance(), cm, cm2);
                    int speedLimit = cam.getSegment().getSpeedLimit();

                    log.info(String.format("speed: %d | speedlimit:%d", speed, speedLimit));
                    if (speed > speedLimit) {
                        log.info(String.format("Licenseplate %s will receive a speeding fine. speedlimit= %d, speed= %d)", lp.getPlateId(), speedLimit, speed));
                        return new SpeedingViolation(speed, speedLimit, lp, cam, cm, segment);
                    }
                }
            }
        }
        return null;
    }

    private int calculateSpeed(int distance, CameraMessage firstMessage, CameraMessage secondMessage) throws ArithmeticException {
        int firstMessageTime = firstMessage.getDateTime().toLocalTime().toSecondOfDay();
        int secondMessageTime = secondMessage.getDateTime().toLocalTime().toSecondOfDay();
        int time = secondMessageTime - firstMessageTime;
        return distance / time;
    }
}
