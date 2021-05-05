package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidUserPassException extends RuntimeException {

    private final static String INVALID_USER_PASS = "Invalid user or pass";

    public InvalidUserPassException() {
        super(String.format(INVALID_USER_PASS));
    }

}
