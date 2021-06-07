package app.api.income;

import app.model.Exceptions.InvalidAmountException;
import app.model.Exceptions.InvalidDateException;
import app.model.Exceptions.NotFoundIncome;
import app.model.Income.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncomeService {

    private final static String ID_NOT_FOUND = "Id %s not found";

    @Autowired
    private final IncomeRepository incomeRepository;

    //Constructor
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public Income saveIncome (Income income){
        if(!income.isAvailable()) {
            throw new InvalidAmountException();
        }
       return incomeRepository.save(income);
    }

    public List<Income> getIncomeHistory(String id, LocalDateTime from, LocalDateTime to){

        Long longID;

        try {
            longID = Long.parseLong(id);
        }
        catch (Exception e){
            throw new UsernameNotFoundException(String.format(ID_NOT_FOUND, id));
        }

        return incomeRepository.getFiltered(longID, from, to);

    }

    public List<Income> getIncomeHistory(String id){

        Long longID = null;

        try {
            longID = Long.parseLong(id);
        }
        catch (Exception e){
            new UsernameNotFoundException(String.format(ID_NOT_FOUND, id));
        }

        return incomeRepository.findByUserId(longID);

    }

    public long checkAmount(long id, Integer amount) {
        Integer oldAmount = incomeRepository.findById(id).getAmount();
        Integer finalAmount = 0;

        if(!oldAmount.equals(amount)){
            finalAmount =  amount - oldAmount;
        }
        return finalAmount;
    }

    public NotFoundIncome existIncome(long id) {
        Income income = incomeRepository.findById(id);
        if(income == null){
            throw new NotFoundIncome();
        }
        return null;
    }

}
