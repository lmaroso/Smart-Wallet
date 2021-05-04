package app.model.Validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface PhoneNumberValidation {

    String message() default "validations.invalidPhoneNumber";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}