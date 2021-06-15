package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidProgrammedValuesException extends RuntimeException {

    private final static String NOT_VALID_VALUES = "Programmed values are not valid";

    public InvalidProgrammedValuesException(){super(String.format(NOT_VALID_VALUES));}

}
