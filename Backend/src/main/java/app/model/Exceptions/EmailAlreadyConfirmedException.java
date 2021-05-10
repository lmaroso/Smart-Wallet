package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmailAlreadyConfirmedException extends RuntimeException {

    private final static String ALREADY_CONFIRMED = "Email already confirmed";

    public EmailAlreadyConfirmedException(){super(String.format(ALREADY_CONFIRMED));}
}
