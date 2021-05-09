package app.api.user;

import app.model.Exceptions.InvalidEmailException;
import app.model.Exceptions.UsedEmailException;
import app.model.User.User;
import app.model.Validators.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User %s not found";

    @Autowired
    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.emailValidator = new EmailValidator();
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    //Methods
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.findUserByEmail(email);
    }

    public String registerUser(User user) {
        boolean isValidEmail = this.emailValidator.test(user.getEmail());
        if (!isValidEmail){
            throw new InvalidEmailException(user.getEmail());
        }
        return this.signUpUser(user);
    }

    public User findUserNamed(String name) {
        return userRepository.findByName(name);
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(User user){
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userExists){
            throw new UsedEmailException(user.getEmail());
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Se guardo correctamente";
    }

}
