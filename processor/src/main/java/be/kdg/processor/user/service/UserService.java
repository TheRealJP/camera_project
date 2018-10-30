package be.kdg.processor.user.service;

import be.kdg.processor.user.exceptions.UserNotFoundException;
import be.kdg.processor.user.model.User;
import be.kdg.processor.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService /*implements UserDetailsService*/ {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        final User user = userRepo.findByUserName(username);
//
//        if (user != null) {
//            final List<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority("USER"));
//            return new CustomUserDetails(user.getUserName(), user.getPassWord(), user.getId(), authorities);
//
//        } else throw new UsernameNotFoundException("User '" + username + "' not found.");
//    }

    public User get(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) return optionalUser.get();
        throw new UserNotFoundException("User not found");
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User save(User newUser) {
        return userRepo.save(newUser);
    }

    /**
     * return type can only be Integer/void with @Modifying, chose integer to have at least some form of confirmation
     */
    public Integer update(Long id, User newUserData) {
        return userRepo.updateUserById(newUserData.getUserName(), newUserData.getPassWord(), id);
    }

    public User remove(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepo.delete(user);
            return user;
        } else throw new UserNotFoundException("User not found");
    }
}
