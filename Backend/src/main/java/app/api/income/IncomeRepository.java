package app.api.income;

import app.model.User.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Override
    <S extends Income> S save(S entity);
}
