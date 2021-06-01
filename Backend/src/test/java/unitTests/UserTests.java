package unitTests;

import app.model.Exceptions.InvalidEmailException;
import app.model.Expense.Expense;
import app.model.User.User;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserTests {

    @Test
    public void userProperties(){

        User user = new User("Smart", "smart.wallet.app1@gmail.com", "sw");

        assertEquals("Smart", user.getName());
        assertEquals("smart.wallet.app1@gmail.com", user.getUsername());
        assertEquals("sw", user.getPassword());
        assertEquals(0, user.getAccountCredit(), 0);
        assertEquals(0, user.getAccountExpense(), 0);
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertFalse(user.isEnabled());

    }

    @Test
    public void setName(){

        User user = new User("Smart", "smart.wallet.app1@gmail.com", "sw");

        String expect = "Sm";
        user.setName("Sm");

        assertEquals(expect, user.getName());

    }

    @Test
    public void setEmail(){

        User user = new User("Smart", "smart.wallet.app1@gmail.com", "sw");

        String expect = "smart.wallet.app@com";
        user.setEmail("smart.wallet.app@com");

        assertEquals(expect, user.getUsername());

    }

    @Test
    public void setPassword(){

        User user = new User("Smart", "smart.wallet.app1@gmail.com", "sw");

        String expect = "SmartW";
        user.setPassword("SmartW");

        assertEquals(expect, user.getPassword());

    }

    @Test(expected = InvalidEmailException.class)
    public void invalidEmailException(){

        User user = new User("Smart", "smart.wallet.app1", "sw");

    }

}