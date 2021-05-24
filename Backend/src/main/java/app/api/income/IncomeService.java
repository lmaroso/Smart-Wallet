package app.api.income;

import app.model.Exceptions.InvalidAmountException;
import app.model.Income.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

    @Autowired
    private final IncomeRepository incomeRepository;

    //Constructor
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public Income saveIncome (Income income){
        if(!income.isAvailable()) {
            throw new InvalidAmountException();
        }
       return incomeRepository.save(income);
    }

}
