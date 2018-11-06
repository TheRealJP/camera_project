package be.kdg.processor.user.service;

import be.kdg.processor.user.exceptions.UserNotFoundException;
import be.kdg.processor.user.model.CustomUserDetails;
import be.kdg.processor.user.model.User;
import be.kdg.processor.user.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepo.findByUserName(username);
        if (user.isPresent()) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new CustomUserDetails(user.get().getUserName(), user.get().getPassWord(), user.get().getId(), authorities);
        } else {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
    }


    public User get(String userName) {
        Optional<User> optionalUser = userRepo.findByUserName(userName);
        return optionalUser.orElseGet(User::new);
    }


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
     * returns empty user if user not found
     */
    public User update(Long id, User newUserData) throws UserNotFoundException {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            user.get().setUserName(newUserData.getUserName());
            user.get().setPassWord(newUserData.getPassWord());
            return save(user.get());
        } else throw new UserNotFoundException("User not found");
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
