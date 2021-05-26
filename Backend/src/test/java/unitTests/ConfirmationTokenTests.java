package unitTests;

import app.model.Token.ConfirmationToken;
import app.model.User.User;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class ConfirmationTokenTests {

    @Test
    public void confirmationTokenProperties(){

        LocalDateTime now = LocalDateTime.now();
        User user = new User("Smart", "smart.wallet.app1@gmail.com", "sw");
        ConfirmationToken token = new ConfirmationToken("123456789", now, now.plusMinutes(15), user);

        assertEquals(now.plusMinutes(15), token.getExpiresAt());
        assertEquals("smart.wallet.app1@gmail.com", token.getUser().getUsername());

    }

}
