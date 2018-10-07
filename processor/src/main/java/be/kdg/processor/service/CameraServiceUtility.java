package be.kdg.processor.service;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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


    public boolean emissionCheck(CameraMessage cm) {
        try {
            Camera camera = collectCamera(cm);
            LicensePlate lp = collectLicensePlate(cm);
            if (camera.getEuroNorm() > lp.getEuroNumber()) {
                log.info(String.format("Licenseplate %s will receive a emission fine. cameraNorm=%d, carNorm=%d)", lp.getPlateId(), camera.getEuroNorm(), lp.getEuroNumber()));
                return true;
            }

        } catch (IOException e) {
            log.error(e.getMessage() + " | foute nummerplaat");
        }

        return false;
    }

    public boolean speedingCheck(CameraMessage cm) {
        //distance between 2 cameras
        //calculate speed

        return false;
    }

    private Camera collectCamera(CameraMessage cm) throws IOException {
        String camJson = camProxy.get(cm.getId());
        return objectMapper.readValue(camJson, Camera.class);
    }

    private LicensePlate collectLicensePlate(CameraMessage cm) throws IOException {
        String lpJson = lpProxy.get(cm.getLicensePlate());
        return objectMapper.readValue(lpJson, LicensePlate.class);
    }


}
