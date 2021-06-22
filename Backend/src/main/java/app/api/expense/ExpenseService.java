package app.api.expense;

import app.model.Exceptions.InvalidAmountException;
import app.model.Exceptions.InvalidProgrammedValuesException;
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

    public Expense existExpense(long id) {
        Expense expense = expenseRepository.findById(id);
        if(expense == null){
            throw new NotFoundExpense();
        }
        return expense;
    }

    public void checkValidProgrammedValues(Expense expense){
        if(expense.isProgrammed() &&
           expense.getRepetitionMilliSeconds() == 0 &&
           expense.getDayOfWeek() == 0 &&
           expense.getDayOfMonth() == 0 &&
           !expense.isCancelled()) {
            throw new InvalidProgrammedValuesException();
        }
    }

    public void deleteExpense(String id) {
        Long longID = null;

        try {
            longID = Long.parseLong(id);
        }
        catch (Exception e){
            new UsernameNotFoundException(String.format(ID_NOT_FOUND, id));
        }

        this.existExpense(longID);
        expenseRepository.deleteById(longID);
    }

    public void cancelExpense(long id){
        Expense expense = expenseRepository.findById(id);
        Expense canceledExpense;
        long creatorId = expense.getCreatorId();
        if(creatorId != 0){
            canceledExpense = expenseRepository.findById(creatorId);
        }
        else{
            canceledExpense = expense;
        }
        if(canceledExpense == null){
            throw new NotFoundExpense();
        }
        canceledExpense.setCancelled(true);
        expenseRepository.save(canceledExpense);
    }

}
