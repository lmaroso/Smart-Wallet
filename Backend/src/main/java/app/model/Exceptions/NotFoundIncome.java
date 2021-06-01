package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotFoundIncome extends RuntimeException{

    private final static String NOT_FOUND_INCOME = "Not found income";

    public NotFoundIncome(){
        super(String.format(NOT_FOUND_INCOME));
    }

}
