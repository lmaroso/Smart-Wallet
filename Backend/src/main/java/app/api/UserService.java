package app.api;

import app.model.Exceptions.InvalidEmailException;
import app.model.User.User;
import app.model.Validators.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User %s not found";

    @Autowired
    private final UserRepository userRepository;
    private final EmailValidator emailValidator;

    //Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.emailValidator = new EmailValidator();
    }

    //Methods
    public String registerUser(User user) {
        boolean isValidEmail = this.emailValidator.test(user.getEmail());
        if (!isValidEmail){
            throw new InvalidEmailException(user.getEmail());
        }
        userRepository.save(user);
        return "Se guardo correctamente";
    }
    public User findUserNamed(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }
}
