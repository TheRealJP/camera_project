package be.kdg.processor.cameramessage.controllers;


import be.kdg.processor.cameramessage.models.Retryable;
import be.kdg.processor.cameramessage.observerpattern.listeners.RetryableListener;
import be.kdg.processor.cameramessage.service.retryable.RetryableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    public RetryableWebController(RetryableService retryableService) {
        this.retryableService = retryableService;
    }

    @GetMapping("/retryableSettings.do")
    public ModelAndView getRetryableSettings() {
        Retryable retryable = retryableService.getRetryable();
        return new ModelAndView("retryablesummary", "retryable", retryable);
    }

    @PostMapping("/retryableSettings.do")
    public ModelAndView postFineFactors(@ModelAttribute("retryable") Retryable retryable, Model model) {
        Retryable retryableOut = retryableService.updateRetryable(retryable);
        return new ModelAndView("retryablesummary", "retryable", retryableOut);
    }

}
