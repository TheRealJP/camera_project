package be.kdg.processor.user.service;

import be.kdg.processor.user.model.User;
import be.kdg.processor.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> getUser(Long id) {
        return userRepo.findById(id);
    }
}
