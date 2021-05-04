package app.model.User;

import app.model.Exceptions.InvalidEmailException;
import app.model.Exceptions.InvalidPhoneNumberException;
import app.model.Validators.EmailValidation;
import app.model.Validators.PhoneNumberValidation;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
public class User {

    //Parameters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @NotEmpty(message = "validations.name")
    public String name;

    @EmailValidation
    public String email;

    @PhoneNumberValidation
    public String phone;

    public double accountCredit;

    //Constructor
    public User() {}

    public User(String name, String email, String phone) {

        isAValidPhoneNumber(phone);
        isAValidEmail(email);

        this.name = name;
        this.email = email;
        this.phone = phone;
        this.accountCredit = 0;
    }

    //Methods
    protected void isAValidPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        if (!phoneNumber.matches("^(?:(?:00|\\+)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$"))
            throw new InvalidPhoneNumberException();
    }

    private void isAValidEmail(String newEmail) {
        if (!newEmail.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"))
            throw new InvalidEmailException();
    }

}