package app.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TokenExpiredException extends RuntimeException {

    private final static String TOKEN_EXPIRED = "Token expired";

    public TokenExpiredException(){super(String.format(TOKEN_EXPIRED));
    }
}
