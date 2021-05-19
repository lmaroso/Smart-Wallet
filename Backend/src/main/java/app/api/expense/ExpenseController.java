package app.api.expense;

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

    @PostMapping(value = "/addExpense")
    public void addIncome(@RequestBody ExpenseDTO expenseDTO) {
        Expense expense = new Expense(expenseDTO.getType(), expenseDTO.getName(), expenseDTO.getDescription(), expenseDTO.getAmount(), expenseDTO.getDate(), expenseDTO.getProgrammed());
        expenseService.saveExpense(expense);
    }
}