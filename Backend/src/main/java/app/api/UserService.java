package app.api;

import app.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    //Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Methods
    public User addUser(User user) {
        return userRepository.save(user);
    }
    public User findUserNamed(String name) {
        return userRepository.findByName(name);
    }

}
