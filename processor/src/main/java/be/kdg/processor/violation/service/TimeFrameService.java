package be.kdg.processor.violation.service;

import be.kdg.processor.violation.models.TimeFrame;
import be.kdg.processor.violation.repository.TimeFrameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeFrameService {
    private static final Logger log = LoggerFactory.getLogger(TimeFrameService.class);
    @Autowired
    private SpeedViolationService svs;
    @Autowired
    private EmissionViolationService evs;
    @Autowired
    private TimeFrameRepository timeFrameRepo;

    public TimeFrame getTimeFrame() {
        Optional<TimeFrame> t = timeFrameRepo.findById(0L);
        if (t.isPresent()) {
            log.info("timeframeDTO:" + t.get().toString());
            return t.get();
        }

        TimeFrame tf = new TimeFrame();
        tf.setId(0L);
        tf.setEmissionTimeFrame(86400000); //1 day
        tf.setSpeedTimeFrame(1800000); //30min
        return timeFrameRepo.save(tf);
    }

    public TimeFrame updateTimeFrame(TimeFrame timeFrame) {
        Optional<TimeFrame> tf = timeFrameRepo.findById(0L);
        TimeFrame timeframeIn;

        if (tf.isPresent()) {
            timeframeIn = tf.get();
            timeframeIn.setSpeedTimeFrame(timeFrame.getSpeedTimeFrame());
            timeframeIn.setEmissionTimeFrame(timeFrame.getEmissionTimeFrame());
            TimeFrame timeFrameOut = timeFrameRepo.save(timeframeIn);

            //update timeframeDTO in services
            svs.setTimeFrame(timeFrameOut.getSpeedTimeFrame());
            evs.setTimeFrame(timeFrameOut.getEmissionTimeFrame());
            return timeFrameOut;
        }

        return timeFrame;
    }

}
