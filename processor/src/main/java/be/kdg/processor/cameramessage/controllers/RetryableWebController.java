package be.kdg.processor.cameramessage.controllers;


import be.kdg.processor.cameramessage.dto.RetryDTO;
import be.kdg.processor.cameramessage.models.Retryable;
import be.kdg.processor.observer.listeners.RetryableListener;
import be.kdg.processor.cameramessage.service.retryable.RetryableService;
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
@RequestMapping("/retryable")
public class RetryableWebController {
    private static final Logger log = LoggerFactory.getLogger(RetryableListener.class);
    private final RetryableService retryableService;
    private final ModelMapper modelMapper;

    public RetryableWebController(RetryableService retryableService, ModelMapper modelMapper) {
        this.retryableService = retryableService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/retryableSettings.do")
    public ModelAndView getRetryableSettings() {
        Retryable retryable = retryableService.getRetryable();
        RetryDTO retryDTO = modelMapper.map(retryable, RetryDTO.class);
        return new ModelAndView("retryablesummary", "retryableDTO", retryDTO);
    }

    @PostMapping("/retryableSettings.do")
    public ModelAndView postFineFactors(@ModelAttribute("retryableDTO") RetryDTO retryDTO) {
        Retryable retryable = modelMapper.map(retryDTO, Retryable.class);
        Retryable retryableOut = retryableService.updateRetryable(retryable);
        RetryDTO retryDTOOut = modelMapper.map(retryableOut, RetryDTO.class);
        return new ModelAndView("retryablesummary", "retryableDTO", retryDTOOut);
    }

}
