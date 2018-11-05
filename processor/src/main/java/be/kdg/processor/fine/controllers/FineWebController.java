package be.kdg.processor.fine.controllers;

import be.kdg.processor.fine.dto.FineFactorDTO;
import be.kdg.processor.fine.models.FineFactor;
import be.kdg.processor.fine.service.FineFactorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/fines")
public class FineWebController {
    private static final Logger log = LoggerFactory.getLogger(FineWebController.class);
    private final FineFactorService ffService;
    private final ModelMapper modelMapper;


    public FineWebController(FineFactorService ffService, ModelMapper modelMapper) {
        this.ffService = ffService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/finefactors.do")
    public ModelAndView showFineFactors() {
        FineFactor ff = ffService.getFineFactor();
        FineFactorDTO fineFactorDTO = modelMapper.map(ff, FineFactorDTO.class);
        return new ModelAndView("finefactorsummary", "fineFactorDTO", fineFactorDTO);
    }

    @PostMapping("/finefactors.do")
    public ModelAndView postFineFactors(@ModelAttribute("factorDTO") FineFactorDTO factorDTO) {
        FineFactor ff = modelMapper.map(factorDTO, FineFactor.class);
        FineFactor ffOut = ffService.updateFineFactor(ff);
        FineFactorDTO fineFactorDTO = modelMapper.map(ffOut, FineFactorDTO.class);
        return new ModelAndView("finefactorsummary", "fineFactorDTO", fineFactorDTO);
    }
}

