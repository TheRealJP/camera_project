package be.kdg.processor.user.repositories;

import be.kdg.processor.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    @Modifying
    @Query("update User u set u.userName = ?1, u.passWord = ?2 where u.id = ?3")
    Integer updateUserById(String userName, String passWord, Long userId);


}
