package be.kdg.processor.observer.events;

import be.kdg.processor.cameramessage.models.CameraMessage;
import be.kdg.processor.proxy.models.Camera;
import be.kdg.processor.proxy.models.LicensePlate;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;


public class ConsumeEvent extends ApplicationEvent {
    private final CameraMessage cameraMessage;
    private final Camera camera;
    private final LicensePlate lp;

    public ConsumeEvent(Object source, CameraMessage cameraMessage, Camera camera, LicensePlate lp) {
        super(source);
        this.cameraMessage = cameraMessage;
        this.camera = camera;
        this.lp = lp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumeEvent that = (ConsumeEvent) o;
        return Objects.equals(cameraMessage, that.cameraMessage) &&
                Objects.equals(camera, that.camera) &&
                Objects.equals(lp, that.lp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cameraMessage, camera, lp);
    }

    public CameraMessage getCameraMessage() {
        return cameraMessage;
    }

    public Camera getCamera() {
        return camera;
    }

    public LicensePlate getLp() {
        return lp;
    }
}
