package be.kdg.processor.fine.controllers;

import be.kdg.processor.fine.dto.FineFactorDTO;
import be.kdg.processor.fine.dto.FineFactorDTOs;
import be.kdg.processor.fine.service.FineDTOMapper;
import be.kdg.processor.fine.service.FineFactorService;
import be.kdg.processor.violation.service.TimeFrameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/fines")
public class FineWebController {
    private static final Logger log = LoggerFactory.getLogger(FineWebController.class);
    private final FineFactorService ffService;
    private final TimeFrameService tfService;
    private final FineDTOMapper fineDTOMapper;


    public FineWebController(FineFactorService ffService, TimeFrameService tfService, FineDTOMapper fineDTOMapper) {
        this.ffService = ffService;
        this.tfService = tfService;
        this.fineDTOMapper = fineDTOMapper;
    }

    @GetMapping("/finefactors.do")
    public ModelAndView showFineFactors() {
        List<FineFactorDTO> fineFactorDTOList = fineDTOMapper.toFineFactorDTOList(ffService.getAll());

        ModelAndView mav = new ModelAndView();
        mav.addObject("fineFactorDTOS", fineFactorDTOList);
        mav.setViewName("finefactorsummary");

        return mav;
    }

    @PostMapping("/finefactors.do")
    public ModelAndView postFineFactors(@ModelAttribute("factors") FineFactorDTOs fineFactorDTOs, Model model) {
        fineFactorDTOs.getFineFactorDTOS().forEach(ff -> log.info(ff.getViolationType() + " " + ff.getFactor()));
        return new ModelAndView("finefactorsummary", "fineFactorDTOs", fineFactorDTOs);
    }

    //timeframe
    //TODO move to violationController
    @PostMapping("/timeframes.do")
    public ModelAndView getTimeFrames() {
        return new ModelAndView();
    }
}
