package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends RuntimeException {

    private final static String NOT_VALID_EMAIL = "Email %s is not valid";

    public InvalidEmailException(String email) {
        super(String.format(NOT_VALID_EMAIL, email));
    }
}
