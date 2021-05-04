package app.model.Exceptions;

public class InvalidEmailException extends  RuntimeException {

    public InvalidEmailException() {
        super("El email no es valido");
    }
}
