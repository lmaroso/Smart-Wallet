package app.model.Validators;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String value) {
        return value.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    }
}