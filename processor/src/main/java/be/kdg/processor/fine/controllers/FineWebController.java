package be.kdg.processor.fine.controllers;

import be.kdg.processor.fine.dto.FineFactorDTO;
import be.kdg.processor.fine.service.FineDTOMapper;
import be.kdg.processor.fine.service.FineFactorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/fines")
public class FineWebController {
    private final FineFactorService ffService;
    private final FineDTOMapper fineDTOMapper;

    public FineWebController(FineFactorService ffService, FineDTOMapper fineDTOMapper) {
        this.ffService = ffService;
        this.fineDTOMapper = fineDTOMapper;
    }

    @GetMapping("/finefactors.do") //******.do
    public ModelAndView showFineFactors() {
        List<FineFactorDTO> fineFactorDTOList = fineDTOMapper.toFineFactorDTOList(ffService.getAll());
        return new ModelAndView("finefactorsummary", "fineFactorDTOS", fineFactorDTOList);
    }
}
