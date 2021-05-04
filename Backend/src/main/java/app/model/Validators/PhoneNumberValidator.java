package app.model.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^(?:(?:00|\\+)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$");
    }
}