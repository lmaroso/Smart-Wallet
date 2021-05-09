package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TokenNotFoundException extends RuntimeException {

    private final static String TOKEN_NOT_FOUND = "Token not found";

    public TokenNotFoundException(){ super(String.format(TOKEN_NOT_FOUND));}

}
