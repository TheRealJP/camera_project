package be.kdg.processor.fine.dto;

import be.kdg.processor.fine.models.Fine;
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

    public List<FineDTO> toFineDTOList(List<Fine> fines){
        return fines
                .stream()
                .map(fine -> modelMapper.map(fine, FineDTO.class))
                .collect(Collectors.toList());
    }
}
