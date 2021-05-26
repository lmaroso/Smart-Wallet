package unitTests;

import app.model.Expense.Expense;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ExpenseTests {

    @Test
    public void expenseProperties(){

        LocalDateTime now = LocalDateTime.now();

        Expense expense = new Expense(Long.valueOf(1), "Alquiler", "Alquiler mensual", 35000, now, false);

        assertEquals(Long.valueOf(1), expense.getUserId());
        assertEquals("Alquiler", expense.getName());
        assertEquals("Alquiler mensual", expense.getDescription());
        assertEquals(Integer.valueOf(35000), expense.getAmount());
        assertEquals(now, expense.getDate());
        assertFalse(expense.isProgrammed());

    }

    @Test
    public void setProgrammedExpense(){

        Expense expense = new Expense(Long.valueOf(1), "Alquiler", "Alquiler mensual", 35000, LocalDateTime.now(), false);

        expense.setProgrammed(true);

        assertTrue(expense.isProgrammed());

    }

}
