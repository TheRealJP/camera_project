package be.kdg.processor.service;

import be.kdg.sa.services.CameraServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Component
public class CameraServiceUtility {

    private final CameraServiceProxy camProxy;
    private final Logger log = LoggerFactory.getLogger(CameraServiceUtility.class);

    public CameraServiceUtility(CameraServiceProxy camProxy) {
        this.camProxy = camProxy;
    }

    @PostConstruct
    public void checkCameras() {
        try {
//            {"cameraId":1,"location":{"lat":51.231932,"long":4.502442},"segment":{"connectedCameraId":2,"distance":4300,"speedLimit":70}}
            int[] cameras = new int[]{1, 2, 3, 4, 5/*, 101, 465*/};
            for (int cId : cameras) {
                log.info(camProxy.get(cId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
