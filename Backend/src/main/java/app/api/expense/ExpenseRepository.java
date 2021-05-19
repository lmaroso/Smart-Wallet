package app.api.expense;

import app.model.Expense.Expense;
import app.model.Income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Override
    <S extends Expense> S save(S entity);
}