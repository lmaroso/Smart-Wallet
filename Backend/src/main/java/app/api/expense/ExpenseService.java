package app.api.expense;

import app.model.Exceptions.InvalidAmountException;
import app.model.Exceptions.NotFoundExpense;
import app.model.Expense.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final static String ID_NOT_FOUND = "Id %s not found";

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

    public List<Expense> getExpenseHistory(String id, LocalDateTime from, LocalDateTime to){

        Long longID;

        try {
            longID = Long.parseLong(id);
        }
        catch (Exception e){
            throw new UsernameNotFoundException(String.format(ID_NOT_FOUND, id));
        }

        return expenseRepository.getFiltered(longID, from, to);

    }

    public List<Expense> getExpenseHistory(String id){

        Long longID = null;

        try {
           longID = Long.parseLong(id);
        }
        catch (Exception e){
            new UsernameNotFoundException(String.format(ID_NOT_FOUND, id));
        }

       return expenseRepository.findByUserId(longID);

    }

    public Double checkAmount(long id, Double amount) {
        Double oldAmount = expenseRepository.findById(id).getAmount();
        Double finalAmount = 0.0;

        if(!oldAmount.equals(amount)){
            finalAmount =  amount - oldAmount;
        }
        return finalAmount;
    }

    public NotFoundExpense existExpense(long id) {
        Expense expense = expenseRepository.findById(id);
        if(expense == null){
            throw new NotFoundExpense();
        }
        return null;
    }
}
