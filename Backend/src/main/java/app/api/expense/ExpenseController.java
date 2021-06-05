package app.api.expense;

import app.api.user.UserService;
import app.dto.ExpenseDTO;
import app.model.Exceptions.NotFoundExpense;
import app.model.Expense.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        Expense expense = new Expense(expenseDTO.getUserId(),
                expenseDTO.getName(), expenseDTO.getDescription(),
                expenseDTO.getAmount(), expenseDTO.getDate(),
                expenseDTO.getProgrammed());

        userService.updateAccountExpense(expense.getUserId(), expense.getAmount());
        expenseService.saveExpense(expense);

        return HttpStatus.OK;
    }

    @PostMapping(value = "/editExpense")
    public HttpStatus editExpense(@RequestBody ExpenseDTO expenseDTO){
        Expense expense = new Expense(expenseDTO.getId(), expenseDTO.getUserId(),
                expenseDTO.getName(), expenseDTO.getDescription(),
                expenseDTO.getAmount(), expenseDTO.getDate(),
                expenseDTO.getProgrammed());

        userService.updateAccountExpense(expense.getUserId(), expenseService.checkAmount(expense.getId(), expense.getAmount()));
        expenseService.saveExpense(expense);

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
}


