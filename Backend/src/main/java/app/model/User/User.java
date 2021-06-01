package app.model.User;

import app.model.Exceptions.InvalidEmailException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Collections;
import static app.model.User.UserRole.USER;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class User implements UserDetails {

    //Parameters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private double accountCredit;

    private double accountExpense;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean expired;

    private Boolean locked;

    private Boolean enabled;

    //Constructor
    public User() {}

    public User(String name, String email, String password) {

        isAValidEmail(email);

        this.name = name;
        this.email = email;
        this.password = password;
        this.accountCredit = 0;
        this.accountExpense = 0;
        this.role = USER;
        this.expired = false;
        this.locked = false;
        this.enabled = false;
    }


    //Methods
    public long getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public double getAccountCredit() {
        return accountCredit;
    }

    public double getAccountExpense(){
        return accountExpense;
    }

    private void isAValidEmail(String newEmail) {
        if (!newEmail.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"))
            throw new InvalidEmailException(newEmail);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}