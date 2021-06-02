package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotFoundExpense extends RuntimeException{

    private final static String NOT_FOUND_EXPENSE = "Not found expense";

    public NotFoundExpense(){
        super(String.format(NOT_FOUND_EXPENSE));
    }

}

