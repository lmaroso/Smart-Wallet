package app.model.Task;

import app.api.user.UserRepository;

import java.util.Calendar;
import java.util.TimerTask;

public class Task extends TimerTask {

    private long userId;
    private long amount;
    private Boolean programmed;
    private int dayOfWeek;
    private int dayOfMonth;
    private UserRepository userRepository;


    public Task(long userId, long amount, boolean programmed, int dayOfWeek,
                int dayOfMonth, UserRepository userRepository){
        this.userId = userId;
        this.amount = amount;
        this.programmed = programmed;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.userRepository = userRepository;
    }

    @Override
    public void run() {

        Calendar calendar = Calendar.getInstance();

        if (((dayOfWeek != 0) && calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek)   ||
           ((dayOfMonth != 0) && calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) ||
           ((dayOfWeek == 0) && dayOfMonth == 0)){
            userRepository.updateAccountCredit(amount, userId);
        }
        if(!programmed){ //Si la tarea no está programada se ejecuta solo una vez.
            this.cancel();
        }
    }

}
