package be.kdg.processor.user.controllers;


import be.kdg.processor.fine.dto.FineFactorDTO;
import be.kdg.processor.user.dto.UserDTO;
import be.kdg.processor.user.exceptions.UserNotFoundException;
import be.kdg.processor.user.model.User;
import be.kdg.processor.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserWebController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserWebController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login") //******.do
    public ModelAndView loginuser(@ModelAttribute UserDTO userDTO) throws UserNotFoundException {
//        User user = modelMapper.map(userDTO, User.class);
        if (!userService.getUser(userDTO.getId()).isPresent()) {
            return new ModelAndView("loginpage");
//            throw new UserNotFoundException("User not found");
        }

        return new ModelAndView("start");
    }


    // Login form
    @RequestMapping("/loginpage")
    public String login() {
        return "login.html";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(UserDTO userDTO, Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

    @PostMapping("/login")
    public ModelAndView showLogin() {
        return new ModelAndView("loginpage");
    }

}
