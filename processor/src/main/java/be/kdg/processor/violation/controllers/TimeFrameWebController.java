package be.kdg.processor.violation.controllers;

import be.kdg.processor.observer.listeners.RetryableListener;
import be.kdg.processor.violation.dto.TimeFrameDTO;
import be.kdg.processor.violation.models.TimeFrame;
import be.kdg.processor.violation.service.TimeFrameService;
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
@RequestMapping("timeframe")
public class TimeFrameWebController {
    private static final Logger log = LoggerFactory.getLogger(RetryableListener.class);
    private final TimeFrameService timeFrameService;
    private final ModelMapper modelMapper;


    public TimeFrameWebController(TimeFrameService timeFrameService, ModelMapper modelMapper) {
        this.timeFrameService = timeFrameService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/timeFrameSettings.do")
    public ModelAndView getTimeFrameSettings() {
        TimeFrame timeFrame = timeFrameService.getTimeFrame();
        TimeFrameDTO tfDTO = modelMapper.map(timeFrame, TimeFrameDTO.class);
        return new ModelAndView("timeframesummary", "timeframeDTO", tfDTO);
    }

    @PostMapping("/timeFrameSettings.do")
    public ModelAndView postTimeFrame(@ModelAttribute("timeframeDTO") TimeFrameDTO timeFrameDTO) {
        TimeFrame tf = modelMapper.map(timeFrameDTO, TimeFrame.class);
        TimeFrame timeFrameOut = timeFrameService.updateTimeFrame(tf);
        TimeFrameDTO timeFrameDTOOUT = modelMapper.map(timeFrameOut, TimeFrameDTO.class);
        return new ModelAndView("timeframesummary", "timeframeDTO", timeFrameDTOOUT);
    }
}
