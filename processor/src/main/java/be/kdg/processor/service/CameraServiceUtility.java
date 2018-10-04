package be.kdg.processor.service;

import be.kdg.sa.services.CameraServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CameraServiceUtility {

    private final CameraServiceProxy camProxy;
    private final Logger log = LoggerFactory.getLogger(CameraServiceUtility.class);

    public CameraServiceUtility(CameraServiceProxy camProxy) {
        this.camProxy = camProxy;
    }

    @Bean
    //            {"cameraId":1,"location":{"lat":51.231932,"long":4.502442},"segment":{"connectedCameraId":2,"distance":4300,"speedLimit":70}}

    public boolean emissionCheck() {
        try {
            int[] cameras = new int[]{1, 2, 3, 4, 5/*, 101, 465*/};
            for (int cId : cameras) {
                String cam = camProxy.get(cId);
                log.info(camProxy.get(cId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
