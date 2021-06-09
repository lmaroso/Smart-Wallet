package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsedEmailException extends RuntimeException {

    private final static String USED_EMAIL = "The email %s is already used";

    public UsedEmailException(String email){
        super(String.format(USED_EMAIL, email));
    }
}
