package app.api.user;

import app.model.Exceptions.InvalidAmountException;
import app.model.User.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

    @Autowired
    private final IncomeRepository incomeRepository;

    //Constructor
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;

        //this.emailValidator = new EmailValidator();
    }

    public void saveIncome (Income income){
        if(!income.isAvailable()) {
            throw new InvalidAmountException();
        }
        incomeRepository.save(income);
    }

}
