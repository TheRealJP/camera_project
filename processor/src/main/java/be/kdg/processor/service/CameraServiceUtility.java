package be.kdg.processor.service;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Component
public class CameraServiceUtility {

    private final CameraServiceProxy camProxy;
    private final LicensePlateServiceProxy lpProxy;

    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(CameraServiceUtility.class);

    public CameraServiceUtility(CameraServiceProxy camProxy, ObjectMapper objectMapper, LicensePlateServiceProxy lpProxy) {
        this.camProxy = camProxy;
        this.objectMapper = objectMapper;
        this.lpProxy = lpProxy;
    }

    public void emissionCheck(CameraMessage cm) {
        try {
            String lpJson = lpProxy.get(cm.getLicensePlate());
            String camJson = camProxy.get(cm.getId());

            LicensePlate lp = objectMapper.readValue(lpJson, LicensePlate.class);
            Camera camera = objectMapper.readValue(camJson, Camera.class);
            int camNorm = camera.getEuroNorm();
            int lpNorm = lp.getEuroNumber();

            if (camNorm > lpNorm) {
                log.info(String.format("Licenseplate %s will receive a emission fine. cameraNorm=%d, carNorm=%d)", lp.getPlateId(), camera.getEuroNorm(), lp.getEuroNumber()));
            }

        } catch (IOException e) {
            log.error(e.getMessage() + " | foute nummerplaat");
        }
    }


}
