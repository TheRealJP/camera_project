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

    public FineFactor getFineFactor() {
        Optional<FineFactor> ff = Optional.ofNullable(fineFactorRepository.findFirstByEmissionfactorNotNullAndSpeedfactorNotNull());
        if (ff.isPresent()) return ff.get();

        FineFactor newff = new FineFactor();
        newff.setId(0L);
        newff.setSpeedfactor(4);
        newff.setEmissionfactor(150);
        return fineFactorRepository.save(newff);
    }

    public FineFactor updateFineFactor(FineFactor ff) {
        Optional<FineFactor> ffOld = Optional.ofNullable(fineFactorRepository.findFirstByEmissionfactorNotNullAndSpeedfactorNotNull());
        if (ffOld.isPresent()) {
            FineFactor ffIn = ffOld.get();
            ffIn.setSpeedfactor(ff.getSpeedfactor());
            ffIn.setEmissionfactor(ff.getEmissionfactor());
            return fineFactorRepository.save(ffIn);
        }

        //shows it didnt work to viewer
        ff.setEmissionfactor(0);
        ff.setSpeedfactor(0);
        return ff;
    }
}
