package unitTests;

import app.model.Exceptions.InvalidEmailException;
import app.model.User.User;
import org.junit.Test;

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

    @Test(expected = InvalidEmailException.class)
    public void invalidEmailException(){

        User user = new User("Smart", "smart.wallet.app1", "sw");

    }

}
