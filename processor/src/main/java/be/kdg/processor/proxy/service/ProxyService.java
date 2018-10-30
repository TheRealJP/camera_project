package be.kdg.processor.proxy.service;

import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import be.kdg.sa.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
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


    @Cacheable("cameras")
    public Camera collectCamera(int camId) throws IOException, CameraNotFoundException {
        String camJson = camProxy.get(camId);
        return objectMapper.readValue(camJson, Camera.class);
    }

    @Cacheable("licenseplates")
    public LicensePlate collectLicensePlate(String lp) throws IOException, LicensePlateNotFoundException, InvalidLicensePlateException {
        String lpJson = lpProxy.get(lp);
        return objectMapper.readValue(lpJson, LicensePlate.class);
    }
}
