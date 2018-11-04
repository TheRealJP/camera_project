package be.kdg.processor.cameramessage.service.retryable;

import be.kdg.processor.cameramessage.models.Retryable;
import be.kdg.processor.cameramessage.observerpattern.publishers.RetryablePublisher;
import be.kdg.processor.cameramessage.repositories.RetryableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class RetryableService {
    private static final Logger log = LoggerFactory.getLogger(RetryableService.class);

    @Autowired
    private RetryablePublisher publisher;

    @Autowired
    private RetryableRepository settingRepo;

    public RetryableService() {
    }

    public Retryable save(Retryable retryable) {
        return settingRepo.save(retryable);
    }

    public Retryable getRetryable() {
        Optional<Retryable> r = settingRepo.findById(0L);
        if (r.isPresent()) {
            log.info("retryable:" + r.get().toString());
            return r.get();
        }

        Retryable newR = new Retryable();
        newR.setId(0L);
        newR.setRetryableTimeOut(2000);
        newR.setRetryableAttempts(2);
        return settingRepo.save(newR);
    }

    //there is not a retryable object made.
    public Retryable updateRetryable(Retryable retryable) {
        Optional<Retryable> r = settingRepo.findById(0L);
        Retryable retryableIn;

        if (r.isPresent()) {
            log.info("retryable:" + r.get().toString());
            retryableIn = r.get();
            retryableIn.setRetryableAttempts(retryable.getRetryableAttempts());
            retryableIn.setRetryableTimeOut(retryable.getRetryableTimeOut());
            Retryable retryableOut = settingRepo.save(retryableIn);
            log.info("retryable:" + retryableOut);

            publisher.publishEvent(retryableOut);
            return retryableOut;
        }

        retryable.setRetryableTimeOut(0);
        retryable.setRetryableAttempts(0);
        return retryable;
    }


}
