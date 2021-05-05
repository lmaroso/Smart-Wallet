package app.api;

import app.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    Optional<User> findByEmail(String email);
}
