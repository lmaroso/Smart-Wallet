package app.api.expense;

import app.dto.ExpenseDTO;
import app.model.Expense.Expense;
import app.model.Income.Income;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Override
    <S extends Expense> S save(S entity);

    List<Expense> findByUserId(long userId);

}
