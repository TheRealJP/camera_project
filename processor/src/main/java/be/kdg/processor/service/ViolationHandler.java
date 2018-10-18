package be.kdg.processor.service;

import be.kdg.processor.models.messages.CameraMessage;
import be.kdg.processor.models.proxy.Camera;
import be.kdg.processor.models.proxy.LicensePlate;
import be.kdg.processor.models.proxy.Location;
import be.kdg.processor.models.proxy.Segment;
import be.kdg.processor.models.violations.Violation;
import be.kdg.processor.repositories.*;
import be.kdg.processor.service.events.ConsumeEvent;
import be.kdg.processor.service.fineservice.FineService;
import be.kdg.processor.service.violationservice.ViolationService;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
@Transactional
public class ViolationHandler implements ApplicationListener<ConsumeEvent> {

    private final Logger log = LoggerFactory.getLogger(ViolationHandler.class);
    private final ArrayList<ViolationService> violationServices;
    private final FineService fineService;

    private final CameraMessageRepository cmr;
    private final ViolationRepository violationRepository;
    private final LicensePlateRepository lpRepo;
    private final SegmentRepository segmentRepo;
    private final CameraRepository camRepo;
    private final LocationRepository locRepo;

    public ViolationHandler(ArrayList<ViolationService> violationServices, FineService fineService, CameraMessageRepository cmr, ViolationRepository violationRepository, LicensePlateRepository lpRepo, SegmentRepository segmentRepo, CameraRepository camRepo, LocationRepository locRepo) {
        this.violationServices = violationServices;
        this.fineService = fineService;

        this.cmr = cmr;
        this.violationRepository = violationRepository;
        this.lpRepo = lpRepo;
        this.segmentRepo = segmentRepo;
        this.camRepo = camRepo;
        this.locRepo = locRepo;
    }

    /**
     * nieuwe message komt binnen
     * doorsturen naar checkviolation
     * vergelijken met andere messages
     * opslagen in volgorde van FK references
     */
    @Override
    public void onApplicationEvent(ConsumeEvent event) {
        try {
            CameraMessage cm = event.getCameraMessage();
            cmr.save(cm);

            Collection<CameraMessage> cameraMessages = cmr.findAll();
            if (cameraMessages.size() > 0) {

                for (ViolationService vs : violationServices) {
                    Violation violation = vs.checkViolation(cm, cameraMessages);
                    if (violation != null) {
                        Segment segment = violation.getCam().getSegment();
                        Location location = violation.getCam().getLocation();
                        Camera cam = violation.getCam();
                        LicensePlate licensePlate = violation.getLicensePlate();

                        if (segment != null)
                            segmentRepo.save(segment);
                        if (location != null)
                            locRepo.save(location);
                        if (cam != null && location != null && segment != null)
                            camRepo.save(cam);
                        if (violation.getLicensePlate() != null)
                            lpRepo.save(licensePlate);

                        violationRepository.save(violation);
                        fineService.createAndSaveFine(violation);
                    }
                }
            }
        } catch (IOException | LicensePlateNotFoundException | CameraNotFoundException | NullPointerException e) {
            log.error(String.format("cause of error: %s", e.getMessage()));
        }
    }
}
