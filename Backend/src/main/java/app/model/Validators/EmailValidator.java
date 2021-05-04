package app.model.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    }
}