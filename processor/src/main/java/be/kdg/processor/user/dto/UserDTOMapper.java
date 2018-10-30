package be.kdg.processor.user.dto;

import be.kdg.processor.user.model.User;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTOMapper {
    private final ModelMapper modelMapper;

    public UserDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> toUserDTOList(List<User> users) {
        return users
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
