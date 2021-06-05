package app.api.user;

import app.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.accountCredit = a.accountCredit+:income WHERE a.id = :id")
    void updateAccountCredit (double income, long id );

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.accountExpense = a.accountExpense+:expense WHERE a.id = :id")
    void updateAccountExpense (double expense, long id );

}
