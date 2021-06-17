package app.api.expense;

import app.api.user.UserService;
import app.dto.ExpenseDTO;
import app.model.Exceptions.InvalidDateException;
import app.model.Exceptions.NotFoundExpense;
import app.model.Expense.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@CrossOrigin
@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/addExpense")
    public HttpStatus addExpense(@RequestBody ExpenseDTO expenseDTO) {
        Expense expense = new Expense(expenseDTO.getId(), expenseDTO.getUserId(),
                expenseDTO.getName(), expenseDTO.getDescription(),
                expenseDTO.getAmount(), expenseDTO.getDate(), expenseDTO.getProgrammed(),
                false, true, expenseDTO.getRepetitionMilliSeconds(),
                expenseDTO.getDayOfWeek(), expenseDTO.getDayOfMonth());

        expenseService.checkValidProgrammedValues(expense);
        expenseService.saveExpense(expense);
        userService.createExpenseTask(expense);

        return HttpStatus.OK;
    }

    @PostMapping(value = "/editExpense")
    public HttpStatus editExpense(@RequestBody ExpenseDTO expenseDTO){

        Expense e = expenseService.existExpense(expenseDTO.getId());

        Expense expense = new Expense(expenseDTO.getId(), expenseDTO.getUserId(),
                expenseDTO.getName(), expenseDTO.getDescription(),
                expenseDTO.getAmount(), expenseDTO.getDate(), expenseDTO.getProgrammed(),
                e.isCancelled(), false,  expenseDTO.getRepetitionMilliSeconds(),
                expenseDTO.getDayOfWeek(), expenseDTO.getDayOfMonth());

        expenseService.checkValidProgrammedValues(expense);
        userService.updateAccountExpense(expense.getUserId(), expenseService.checkAmount(expense.getId(), expense.getAmount()));
        expenseService.saveExpense(expense);

        return HttpStatus.OK;
    }

    @PostMapping(value = "/cancelExpense/{id}")
    public HttpStatus cancelExpense(@PathVariable ("id") String id){

        Long longID = null;

        try {
            longID = Long.parseLong(id);
        }
        catch (Exception e){
            throw new NotFoundExpense();
        }

        expenseService.cancelExpense(longID);

        return HttpStatus.OK;

    }

    @DeleteMapping(value = "/deleteExpense/{id}")
    public HttpStatus deleteExpense(@PathVariable ("id") String id){

        expenseService.deleteExpense(id);

        return HttpStatus.OK;
    }

    @GetMapping(value = "/getExpenseHistory/{id}")
    public ResponseEntity<List<Expense>> getExpense(@PathVariable("id") String id){
        List<Expense> expenses = expenseService.getExpenseHistory(id);
        if(expenses.isEmpty()){
            throw new NotFoundExpense();
        }
        return new ResponseEntity<>(expenses , HttpStatus.OK);
    }

    @GetMapping(value = "/getExpenseHistory/{id}/{from}/{to}")
    public ResponseEntity<List<Expense>> getIncomeFiltered(@PathVariable("id") String id,
                                                          @PathVariable("from") String from,
                                                          @PathVariable("to") String to){

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try{
            List<Expense> expenses = expenseService.getExpenseHistory(id, LocalDateTime.parse(from, fmt), LocalDateTime.parse(to, fmt));
            if(expenses.isEmpty()){
                throw new NotFoundExpense();
            }
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        }catch(DateTimeParseException e){
            throw new InvalidDateException();
        }
    }
}
