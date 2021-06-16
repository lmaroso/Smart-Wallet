package app.model.Task;

import app.api.expense.ExpenseRepository;
import app.api.user.UserRepository;
import app.model.Expense.Expense;

import java.util.Calendar;
import java.util.TimerTask;

public class ExpenseTask extends TimerTask {

    private long expenseId;
    private ExpenseRepository expenseRepository;
    private UserRepository userRepository;


    public ExpenseTask(long incomeId, ExpenseRepository expenseRepository, UserRepository userRepository){
        this.expenseId = incomeId;
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run() {

        Expense expense = expenseRepository.findById(this.expenseId);

        if(expense == null || expense.isCancelled()){
            this.cancel();
        }
        else {
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = expense.getDayOfWeek();
            int dayOfMonth = expense.getDayOfMonth();

            if ((dayOfWeek != 0 && calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek) ||
                    (dayOfMonth != 0 && calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) ||
                    (dayOfWeek == 0 && dayOfMonth == 0)) {
                userRepository.updateAccountExpense(expense.getAmount(), expense.getUserId());
            }
            if (!expense.isProgrammed()) { //Si la tarea no está programada se ejecuta solo una vez.
                this.cancel();
            }
        }
    }

}
