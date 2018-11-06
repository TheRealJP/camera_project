package be.kdg.processor.cameramessage.service.retryable;

import be.kdg.processor.cameramessage.models.Retryable;
import be.kdg.processor.observer.publishers.SettingPublisher;
import be.kdg.processor.cameramessage.repositories.RetryableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class RetryableService {
    private static final Logger log = LoggerFactory.getLogger(RetryableService.class);

    @Autowired
    private SettingPublisher publisher;

    @Autowired
    private RetryableRepository settingRepo;

    public RetryableService() {
    }

    public Retryable getRetryable() {
        Optional<Retryable> r = settingRepo.findById(0L);
        if (r.isPresent()) {
            log.info("retryableDTO:" + r.get().toString());
            return r.get();
        }

        Retryable newR = new Retryable();
        newR.setId(0L);
        newR.setRetryableTimeOut(2000);
        newR.setRetryableAttempts(2);
        return settingRepo.save(newR);
    }


    public Retryable updateRetryable(Retryable retryable) {
        Optional<Retryable> r = settingRepo.findById(0L);
        Retryable retryableIn;

        if (r.isPresent()) {
            retryableIn = r.get();
            retryableIn.setRetryableAttempts(retryable.getRetryableAttempts());
            retryableIn.setRetryableTimeOut(retryable.getRetryableTimeOut());
            Retryable retryableOut = settingRepo.save(retryableIn);

            publisher.publishRetryableEvent(retryableOut);
            return retryableOut;
        }

        retryable.setRetryableTimeOut(0);
        retryable.setRetryableAttempts(0);
        return retryable;
    }


}
