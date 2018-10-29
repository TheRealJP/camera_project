package be.kdg.processor.user.controllers;



import be.kdg.processor.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserWebController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserWebController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/fine")
    
}
