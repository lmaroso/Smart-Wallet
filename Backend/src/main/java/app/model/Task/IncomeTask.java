package app.model.Task;

import app.api.income.IncomeRepository;
import app.api.user.UserRepository;
import app.model.Income.Income;
import java.util.Calendar;
import java.util.TimerTask;

public class IncomeTask extends TimerTask {

    private long incomeId;
    private IncomeRepository incomeRepository;
    private UserRepository userRepository;


    public IncomeTask(long incomeId, IncomeRepository incomeRepository, UserRepository userRepository){
        this.incomeId = incomeId;
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run() {

        Income income = incomeRepository.findById(this.incomeId);

        if(income == null || income.isCancelled()){
            this.cancel();
        }

        else {
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = income.getDayOfWeek();
            int dayOfMonth = income.getDayOfMonth();

            if ((dayOfWeek != 0 && calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek) ||
                    (dayOfMonth != 0 && calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) ||
                    (dayOfWeek == 0 && dayOfMonth == 0)) {
                userRepository.updateAccountCredit(income.getAmount(), income.getUserId());
                if(income.isFirstTask()){
                    income.setFirstTask(false);
                    incomeRepository.save(income);
                }
                else{
                    Income newIncome = new Income(income.getUserId(), income.getName(), income.getDescription(),
                            income.getAmount(), income.getDate(), true, false, false,
                            income.getRepetitionMilliSeconds(), income.getDayOfWeek(),
                            income.getDayOfMonth());
                    newIncome.setCreatorId(income.getId());
                    incomeRepository.save(newIncome);
                }
            }
            if (!income.isProgrammed()) { //Si la tarea no está programada se ejecuta solo una vez.
                this.cancel();
            }
        }
    }

}
