package app.api.expense;

import app.model.Expense.Expense;
import app.model.Income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findById(long id);

    List<Expense> findByUserId(long userId);

    @Transactional
    @Query("SELECT e FROM Expense e " +
            "WHERE e.userId = ?1 AND " +
            "e.date >= ?2 AND " +
            "e.date <= ?3")
    List<Expense> getFiltered(Long id, LocalDateTime from, LocalDateTime to);

}
