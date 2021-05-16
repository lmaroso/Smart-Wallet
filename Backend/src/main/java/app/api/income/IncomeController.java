package app.api.income;

import app.dto.IncomeDTO;
import app.model.User.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping(value = "/addIncome")
    public void addIncome(@RequestBody IncomeDTO incomeDTO) {
        Income income = new Income(incomeDTO.getName(), incomeDTO.getDescription(), incomeDTO.getAmount(), incomeDTO.getDate(), incomeDTO.getProgrammed());
        incomeService.saveIncome(income);
    }
}
