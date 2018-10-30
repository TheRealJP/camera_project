package be.kdg.processor.user.controllers;


import be.kdg.processor.user.dto.UserDTO;
import be.kdg.processor.user.dto.UserDTOMapper;
import be.kdg.processor.user.exceptions.UserNotFoundException;
import be.kdg.processor.user.model.User;
import be.kdg.processor.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private UserDTOMapper userDTOMapper;


    public UserRestController(UserService userService, ModelMapper modelMapper, UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.get(id);
        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(userDTOMapper.toUserDTOList(users), HttpStatus.OK);
    }

    @PostMapping(value = "/users/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@ModelAttribute UserDTO userDTO) {
        User newUser = new User(userDTO.getUserName(), userDTO.getUserName());
        User userOut = userService.save(newUser);
        return new ResponseEntity<>(modelMapper.map(userOut, UserDTO.class), HttpStatus.OK);
    }

    @PutMapping(path = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateUser(@PathVariable Long id,
                                              @RequestParam("username") String userName,
                                              @RequestParam("password") String password) {
        User newUserData = new User(userName, password);
        Integer updatedUser = userService.update(id, newUserData);
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        User removedUser = userService.remove(id);
        return new ResponseEntity<>(modelMapper.map(removedUser, UserDTO.class), HttpStatus.OK);
    }

}
