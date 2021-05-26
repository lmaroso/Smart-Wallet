package unitTests;

import app.model.Validators.EmailValidator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {

    @Test
    public void isAValidEmail(){

        EmailValidator validator = new EmailValidator();

        assertTrue(validator.test("smart.wallet.app1@gmail.com"));

    }

    @Test
    public void isNotValidEmail(){

        EmailValidator validator = new EmailValidator();

        assertFalse(validator.test("smart.wallet.app1gmail.com"));

    }

}
