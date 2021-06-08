package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)

public class InvalidDateException extends RuntimeException {

    private final static String NOT_VALID_DATE = "Invalid date";

    public InvalidDateException() {
        super(String.format(NOT_VALID_DATE));
    }
}
