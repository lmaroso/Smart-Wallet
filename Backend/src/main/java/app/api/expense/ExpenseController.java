package app.api.expense;

import app.api.user.UserService;
import app.dto.ExpenseDTO;
import app.model.Expense.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/addExpense")
    public void addIncome(@RequestBody ExpenseDTO expenseDTO) {
        Expense expense = new Expense(expenseDTO.getUserId(),
                expenseDTO.getName(), expenseDTO.getDescription(),
                expenseDTO.getAmount(), expenseDTO.getDate(),
                expenseDTO.getProgrammed());

        userService.addExpense(expense);
        expenseService.saveExpense(expense);
    }
}