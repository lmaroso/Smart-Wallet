package app.api.user;

import app.dto.ProfileDTO;
import app.dto.UserDTO;
import app.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //Post
    @PostMapping(value = "/register")
    public void registerUser(@RequestBody UserDTO userDTO) {

        User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
        userService.registerUser(user);
    }

    //Get
    @GetMapping(value = "/getProfile/{id}")
    public ProfileDTO findUserById(@PathVariable("id") String id) {
        User user = userService.findUserById(id);
        return new ProfileDTO(user.getName(), user.getUsername());
    }

}
