package app.model.Exceptions;

public class InvalidPhoneNumberException extends RuntimeException  {

    public InvalidPhoneNumberException() {
        super("El número de telefono no es valido");
    }
}
