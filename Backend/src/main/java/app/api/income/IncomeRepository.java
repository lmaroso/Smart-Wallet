package app.api.income;

import app.model.Income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserId(long userId);

    @Transactional
    @Query("SELECT i FROM Income i " +
           "WHERE i.userId = ?1 AND " +
           "i.date >= ?2 AND " +
           "i.date <= ?3")
    List<Income> getFiltered(Long id, LocalDateTime from, LocalDateTime to);

    Income findById(long id);

}
