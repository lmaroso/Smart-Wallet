package unitTests;

import app.model.Email.Controller;
import app.model.Email.Email;
import app.model.Exceptions.InvalidEmailException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EmailTests {

    @Test
    public void emailConstructor() {
        Email email = new Email();
        email.composeEmailWith("Hello", "fedejmartinez@gmail.com","Hello");

        assertEquals("smart.wallet.app1@gmail.com", email.getUser());
        assertEquals("smartwallet2021", email.getPass());
        assertEquals("Hello", email.getSubject());
        assertEquals("fedejmartinez@gmail.com", email.getReceiver());
        assertEquals("Hello", email.getMessage());
    }

    @Test
    public void sendEmail() {
        Email email = new Email();
        email.composeEmailWith("Hello", "fedejmartinez@gmail.com","Hello");

        Controller emailController = mock(Controller.class);

        emailController.sendMail(email);

        verify(emailController, times(1)).sendMail(email);
    }
}
