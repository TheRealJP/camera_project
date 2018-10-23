package be.kdg.processor.fine.service;

import be.kdg.processor.fine.dto.FineDTO;
import be.kdg.processor.fine.dto.FineFactorDTO;
import be.kdg.processor.fine.models.Fine;
import be.kdg.processor.fine.models.FineFactor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FineDTOMapper {
    private final ModelMapper modelMapper;

    public FineDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<FineFactorDTO> toFineFactorDTOList(List<FineFactor> fineFactors) {
        return fineFactors
                .stream()
                .map(ff -> modelMapper.map(ff, FineFactorDTO.class))
                .collect(Collectors.toList());
    }

    public List<FineDTO> toFineDTOList(List<Fine> fines) {
        return fines
                .stream()
                .map(fine -> modelMapper.map(fine, FineDTO.class))
                .collect(Collectors.toList());
    }
}
