package app.api.income;

import app.model.Income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserId(long userId);

    Income findById(long id);

}
