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

    public Optional<FineFactor> getFineFactor(Long id){
        return fineFactorRepository.findById(id);
    }

    public List<FineFactor> getAll(){
        return fineFactorRepository.findAll();
    }

}
