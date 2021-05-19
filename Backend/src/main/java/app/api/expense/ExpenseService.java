package app.api.expense;

import app.model.Exceptions.InvalidAmountException;
import app.model.Expense.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private final ExpenseRepository expenseRepository;

    //Constructor
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;

    }

    public void saveExpense (Expense expense){
        if(!expense.isAvailable()) {
            throw new InvalidAmountException();
        }
        expenseRepository.save(expense);
    }
}
