package be.kdg.processor.fine.service;

import be.kdg.processor.fine.models.FineFactor;
import be.kdg.processor.fine.repositories.FineFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FineFactorService {
    @Autowired
    private FineFactorRepository fineFactorRepository;



    public List<FineFactor> getAll() {
        return fineFactorRepository.findAll();
    }

    public FineFactor getByViolationType(String violationType) {
        Optional<FineFactor> ff = fineFactorRepository.findFineFactorByViolationType(violationType);
        return ff.orElse(null);
    }
}
