package app.api.expense;

import app.model.Expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(long userId);

}
