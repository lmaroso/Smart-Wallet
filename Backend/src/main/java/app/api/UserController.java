package app.api;

import app.dto.userDTO;
import app.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private final UserService userService;

    //Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Post
    @PostMapping(value = "/register")
    public String registerUser(@RequestBody userDTO userDTO) {

        User user = new User(userDTO.name, userDTO.email, userDTO.password);
        return userService.registerUser(user);
    }

    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    public User findUserNamed(@PathVariable("name") String name) {
        return userService.findUserNamed(name);
    }

}
