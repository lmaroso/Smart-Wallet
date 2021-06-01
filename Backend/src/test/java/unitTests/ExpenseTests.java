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
    public void setUserId(){

        Expense expense = new Expense(Long.valueOf(1), "Alquiler", "Alquiler mensual", 35000, LocalDateTime.now(), false);

        Long expect = Long.valueOf(2);
        expense.setUserId(Long.valueOf(2));

        assertEquals(expect, expense.getUserId());

    }

    @Test
    public void setName(){

        Expense expense = new Expense(Long.valueOf(1), "Alquileres", "Alquiler mensual", 35000, LocalDateTime.now(), false);

        String expect = "Alquiler";
        expense.setName("Alquiler");

        assertEquals(expect, expense.getName());

    }

    @Test
    public void setDescription(){

        Expense expense = new Expense(Long.valueOf(1), "Alquiler", "Alquiler mensual", 35000, LocalDateTime.now(), false);

        expense.setDescription("Mensual");
        String expect = "Mensual";

        assertEquals(expect, expense.getDescription());

    }

    @Test
    public void setAmount(){

        Expense expense = new Expense(Long.valueOf(1), "Alquiler", "Alquiler mensual", 35000, LocalDateTime.now(), false);

        Integer expect = 30000;
        expense.setAmount(30000);

        assertEquals(expect, expense.getAmount());

    }

    @Test
    public void setDate(){
        Expense expense = new Expense(Long.valueOf(1), "Alquiler", "Alquiler mensual", 35000, LocalDateTime.now(), false);

        LocalDateTime expect = LocalDateTime.of(2021, 05, 31, 17, 20, 00);
        expense.setDate(LocalDateTime.of(2021, 05, 31, 17, 20, 00));

        assertEquals(expect,expense.getDate() );

    }

    @Test
    public void setProgrammedExpense(){

        Expense expense = new Expense(Long.valueOf(1), "Alquiler", "Alquiler mensual", 35000, LocalDateTime.now(), false);

        expense.setProgrammed(true);

        assertTrue(expense.isProgrammed());

    }

    @Test
    public void setAvailableExpense(){

        Expense expense = new Expense(Long.valueOf(1), "Alquiler", "Alquiler mensual", 0, LocalDateTime.now(), false);

        expense.setAmount(20000);

        assertTrue(expense.isAvailable());

    }

}
