package app.api.income;

import app.api.user.UserService;
import app.dto.IncomeDTO;
import app.model.Exceptions.InvalidDateException;
import app.model.Exceptions.NotFoundIncome;
import app.model.Income.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@CrossOrigin
@RestController
public class IncomeController {
    private final static String ID_NOT_FOUND = "Id %s not found";

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/addIncome")
    public HttpStatus addIncome(@RequestBody IncomeDTO incomeDTO) {

        Income income = new Income (incomeDTO.getId(), incomeDTO.getUserId(),
                    incomeDTO.getName(), incomeDTO.getDescription(), incomeDTO.getAmount(),
                    incomeDTO.getDate(), incomeDTO.getProgrammed(), false, true,
                    incomeDTO.getRepetitionMilliSeconds(), incomeDTO.getDayOfWeek(), incomeDTO.getDayOfMonth());

        incomeService.checkValidProgrammedValues(income);
        incomeService.saveIncome(income);
        userService.createIncomeTask(income);

        return HttpStatus.OK;
    }

    @PostMapping(value = "/editIncome")
    public HttpStatus editIncome(@RequestBody IncomeDTO incomeDTO){

        Income i = incomeService.existIncome(incomeDTO.getId());

        Income income = new Income (incomeDTO.getId(), incomeDTO.getUserId(),
                incomeDTO.getName(), incomeDTO.getDescription(), incomeDTO.getAmount(),
                incomeDTO.getDate(), incomeDTO.getProgrammed(), i.isCancelled(), false,
                incomeDTO.getRepetitionMilliSeconds(), incomeDTO.getDayOfWeek(), incomeDTO.getDayOfMonth());

        incomeService.checkValidProgrammedValues(income);
        userService.updateAccountCredit(income.getUserId(), incomeService.checkAmount(income.getId(), income.getAmount()));
        incomeService.saveIncome(income);

        return HttpStatus.OK;

    }

    @PostMapping(value = "/cancelIncome/{id}")
    public HttpStatus cancelIncome(@PathVariable ("id") String id){

        Long longID = null;

        try {
            longID = Long.parseLong(id);
        }
        catch (Exception e){
            throw new NotFoundIncome();
        }

        incomeService.existIncome(longID);
        incomeService.cancelIncome(longID);

        return HttpStatus.OK;

    }

    @DeleteMapping(value = "/deleteIncome/{idUser}/{id}")
    public HttpStatus deleteIncome(@PathVariable ("idUser") String idUser, @PathVariable ("id") String id){
        Long longID = null;
        Long longIDUser = Long.valueOf(0);

        try {
            longID = Long.parseLong(id);
        } catch (Exception e){
            throw new NotFoundIncome();
        }

        try {
            longIDUser = Long.parseLong(idUser);
        } catch (Exception e){
            new UsernameNotFoundException(String.format(ID_NOT_FOUND, idUser));
        }

        Long idReal = incomeService.existIncome(longID).getUserId();
        userService.updateAccountCredit(idReal, longIDUser, incomeService.checkAmount(longID));
        incomeService.deleteIncome(longID);

        return HttpStatus.OK;
    }

    @GetMapping(value = "/getIncomeHistory/{id}")
    public ResponseEntity<List<Income>> getIncome(@PathVariable("id") String id){

        List<Income> incomes = incomeService.getIncomeHistory(id);
        if(incomes.isEmpty()){
            throw new NotFoundIncome();
        }
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    @GetMapping(value = "/getIncomeHistory/{id}/{from}/{to}")
    public ResponseEntity<List<Income>> getIncomeFiltered(@PathVariable("id") String id,
                                                          @PathVariable("from") String from,
                                                          @PathVariable("to") String to){

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try{
            List<Income> incomes = incomeService.getIncomeHistory(id, LocalDateTime.parse(from, fmt), LocalDateTime.parse(to, fmt));
            if(incomes.isEmpty()){
                throw new NotFoundIncome();
            }
            return new ResponseEntity<>(incomes, HttpStatus.OK);
        }catch(DateTimeParseException e){
            throw new InvalidDateException();
        }
    }

}
