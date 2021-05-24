package app.api.income;

import app.api.user.UserService;
import app.dto.IncomeDTO;
import app.model.Income.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/addIncome")
    public ResponseEntity<Income> addIncome(@RequestBody IncomeDTO incomeDTO) {

        Income income = new Income(incomeDTO.getUserId(),
                incomeDTO.getName(), incomeDTO.getDescription(),
                incomeDTO.getAmount(), incomeDTO.getDate(),
                incomeDTO.getProgrammed());

        userService.updateIncome(income);
        return new ResponseEntity<>(incomeService.saveIncome(income), HttpStatus.CREATED);
    }

}
