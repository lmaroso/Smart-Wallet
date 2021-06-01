package unitTests;

import app.model.Expense.Expense;
import app.model.Income.Income;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class IncomeTests {

    @Test
    public void incomeProperties(){

        LocalDateTime now = LocalDateTime.now();

        Income income = new Income(Long.valueOf(1), "Sueldo", "Sueldo mensual", 35000, now, false);

        assertEquals(Long.valueOf(1), income.getUserId());
        assertEquals("Sueldo", income.getName());
        assertEquals("Sueldo mensual", income.getDescription());
        assertEquals(Integer.valueOf(35000), income.getAmount());
        assertEquals(now, income.getDate());
        assertFalse(income.isProgrammed());

    }

    @Test
    public void setUserId(){

        Income income = new Income(Long.valueOf(1), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);

        Long expect = Long.valueOf(2);
        income.setUserId(Long.valueOf(2));

        assertEquals(expect, income.getUserId());

    }

    @Test
    public void setName(){

        Income income = new Income(Long.valueOf(1), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);

        String expect = "Sueldo bruto";
        income.setName("Sueldo bruto");

        assertEquals(expect, income.getName());

    }

    @Test
    public void setDescription(){

        Income income = new Income(Long.valueOf(1), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);

        income.setDescription("Mensual");
        String expect = "Mensual";

        assertEquals(expect, income.getDescription());

    }

    @Test
    public void setAmount(){

        Income income = new Income(Long.valueOf(1), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);

        Integer expect = 40000;
        income.setAmount(40000);

        assertEquals(expect, income.getAmount());

    }

    @Test
    public void setDate(){
        Income income = new Income(Long.valueOf(1), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);

        LocalDateTime expect = LocalDateTime.of(2021, 05, 31, 17, 20, 00);
        income.setDate(LocalDateTime.of(2021, 05, 31, 17, 20, 00));

        assertEquals(expect,income.getDate() );

    }


    @Test
    public void setProgrammedIncome(){

        Income income = new Income(Long.valueOf(1), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);

        income.setProgrammed(true);

        assertTrue(income.isProgrammed());

    }

    @Test
    public void setAvailableIncome(){

        Income income = new Income(Long.valueOf(1), "Sueldo", "Sueldo mensual", 0, LocalDateTime.now(), false);

        income.setAmount(20000);

        assertTrue(income.isAvailable());

    }

}
