package be.kdg.processor.service.proxyservice;

import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProxyService {
    private final CameraServiceProxy camProxy;
    private final LicensePlateServiceProxy lpProxy;
    private final ObjectMapper objectMapper;

    public ProxyService(CameraServiceProxy camProxy, LicensePlateServiceProxy lpProxy, ObjectMapper objectMapper) {
        this.camProxy = camProxy;
        this.lpProxy = lpProxy;
        this.objectMapper = objectMapper;
    }

    public Camera collectCamera(int camId) throws IOException {
        String camJson = camProxy.get(camId);
        return objectMapper.readValue(camJson, Camera.class);
    }

    public LicensePlate collectLicensePlate(String lp) throws IOException {
        String lpJson = lpProxy.get(lp);
        return objectMapper.readValue(lpJson, LicensePlate.class);
    }
}
