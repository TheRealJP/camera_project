package be.kdg.processor.fine.controllers;

import be.kdg.processor.fine.dto.FineDTOMapper;
import be.kdg.processor.fine.dto.FineFactorDTO;
import be.kdg.processor.fine.service.FineFactorService;
import be.kdg.processor.fine.service.FineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/fines")
public class FineWebController {
    private final FineService fineService;
    private final FineFactorService ffService;
    private final ModelMapper modelMapper;
    private final FineDTOMapper fineDTOMapper;

    public FineWebController(FineService fineService, FineFactorService ffService, ModelMapper modelMapper, FineDTOMapper fineDTOMapper) {
        this.fineService = fineService;
        this.ffService = ffService;
        this.modelMapper = modelMapper;
        this.fineDTOMapper = fineDTOMapper;
    }

    @GetMapping("/finefactors.do") //******.do
    public ModelAndView showFineFactors() {
        List<FineFactorDTO> fineFactorDTOList = fineDTOMapper.toFineFactorDTOList(ffService.getAll());
        return new ModelAndView("finefactorsummary", "fineFactorDTOS", fineFactorDTOList);
    }
}
