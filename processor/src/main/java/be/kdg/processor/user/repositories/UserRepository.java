package be.kdg.processor.user.repositories;

import be.kdg.processor.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
}
