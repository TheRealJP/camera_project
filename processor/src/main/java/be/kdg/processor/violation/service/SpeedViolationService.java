package be.kdg.processor.violation.service;

import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.cameramessage.repositories.CameraMessageRepository;
import be.kdg.processor.observer.events.ConsumeEvent;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.processor.proxy.models.Segment;
import be.kdg.processor.proxy.service.ProxyService;
import be.kdg.processor.violation.models.SpeedingViolation;
import be.kdg.sa.services.CameraNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.ListIterator;

@Service
public class SpeedViolationService implements ViolationService {

    private final Logger log = LoggerFactory.getLogger(SpeedViolationService.class);
    private final ProxyService proxyService;
    private final CameraMessageRepository cmr;
    private long timeFrame;

    public SpeedViolationService(ProxyService proxyService, CameraMessageRepository cmr) {
        this.proxyService = proxyService;
        this.cmr = cmr;
        this.timeFrame = 1800000;
    }


    /**
     * For each message, check if there's another message with the other camera cameraId
     * Scans the messages from now minus the timeframe and loops through them in reverse to get the newest messages first
     * If it's about the same license plate it will compare the measured speed with the speedlimit
     */
    @Override
    public SpeedingViolation checkViolation(ConsumeEvent event) {
        try {
            LicensePlate licensePlate = event.getLp();
            Camera camera = event.getCamera();
            CameraMessage cameraMessage = event.getCameraMessage();

            int otherCameraId = getConnectedSpeedCamera(camera.getCameraId());
            if (otherCameraId != -1) {
                Camera otherCamera = proxyService.collectCamera(otherCameraId);

                LocalDateTime bufferTimeFrame = LocalDateTime.now().minus(timeFrame, ChronoField.MINUTE_OF_DAY.getBaseUnit());
                List<CameraMessage> cameraMessages = cmr.findAllByDateTimeIsAfter(bufferTimeFrame);

                CameraMessage cm;
                for (ListIterator iterator = cameraMessages.listIterator(cameraMessages.size()); iterator.hasPrevious(); ) {
                    cm = (CameraMessage) iterator.previous();
                    boolean sameLicensePlate = cameraMessage.getLicensePlate().equals(cm.getLicensePlate());
                    boolean otherCameraFound = otherCamera.getCameraId() == cm.getCameraId();

                    //check if the message is about the same license plate AND the same other camera
                    if (otherCameraFound && sameLicensePlate) {
                        Segment segment = otherCamera.getSegment();
                        int speed = calculateSpeed(segment.getDistance(), cm, cameraMessage);
                        int speedLimit = segment.getSpeedLimit();

                        log.info("speed: {} | speed limit: {}", speed, speedLimit);
                        if (speed > speedLimit) {
                            log.info("License plate {} will receive a speeding fine. speed limit= {}, speed= {})", licensePlate.getPlateId(), speedLimit, speed);
                            return new SpeedingViolation(speed, speedLimit, licensePlate, camera, cm, segment);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.warn("Speeding Detection: Error reading camera or license plate: " + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * reverse search for the connected camera to the camera with no segment
     */
    private int getConnectedSpeedCamera(int cameraId) {
        int i = 1;
        Camera camera;

        while (true) {
            try {
                camera = proxyService.collectCamera(i);

                if (camera.getSegment() != null && camera.getSegment().getConnectedCameraId() == cameraId)
                    return camera.getCameraId();

                i++;
            } catch (IOException | CameraNotFoundException e) {
                return -1;
            }
        }
    }

    public int calculateSpeed(int distance, CameraMessage firstMessage, CameraMessage secondMessage) throws ArithmeticException {
        int firstMessageTime = firstMessage.getDateTime().toLocalTime().toSecondOfDay();
        int secondMessageTime = secondMessage.getDateTime().toLocalTime().toSecondOfDay();
        int time = Math.abs(secondMessageTime - firstMessageTime);
        return distance / (time == 0 ? 1 : time);
    }

    public void setTimeFrame(long timeFrame) {
        this.timeFrame = timeFrame;
    }
}
