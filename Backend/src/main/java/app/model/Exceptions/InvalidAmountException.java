package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidAmountException extends RuntimeException {

    private final static String AMOUNT_NOT_ALLOW = "Amount is not allowed";

    public InvalidAmountException() {
        super(String.format(AMOUNT_NOT_ALLOW));
    }

}
