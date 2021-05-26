package unitTests;

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
    public void setProgrammedExpense(){

        Income income = new Income(Long.valueOf(1), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);

        income.setProgrammed(true);

        assertTrue(income.isProgrammed());

    }

}
