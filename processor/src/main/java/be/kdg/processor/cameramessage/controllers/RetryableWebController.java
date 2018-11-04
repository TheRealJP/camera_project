package be.kdg.processor.cameramessage.controllers;


import be.kdg.processor.cameramessage.models.Retryable;
import be.kdg.processor.cameramessage.service.retryable.RetryableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/retryable")
public class RetryableWebController {

    private final RetryableService retryableService;

    public RetryableWebController(RetryableService retryableService) {
        this.retryableService = retryableService;
    }

    @GetMapping("/retryableSettings.do")
    public ModelAndView getRetryableSettings() {
        Optional<Retryable> retryable = retryableService.getRetryable();
        if (!retryable.isPresent()) {
            Retryable newRetryable = new Retryable(0L, 2, 2000);
            return new ModelAndView("retryablesummary", "retryable", newRetryable);
        }
        return new ModelAndView("retryablesummary", "retryable", retryable.get());
    }

    @PostMapping("/retryableSettings.do")
    public ModelAndView postFineFactors(@ModelAttribute("retryable") Retryable retryable, Model model) {
        Retryable retryableOut = retryableService.updateRetryable(retryable);
        return new ModelAndView("retryablesummary", "retryableOut", retryableOut);
    }

}
