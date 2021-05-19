package app.api.user;

import app.api.income.IncomeService;
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
    @GetMapping(value = "/getProfile/{email}")
    public ProfileDTO findUserByEmail(@PathVariable("email") String email) {
        User user = userService.findUserByEmail(email);
        return new ProfileDTO(user.getName(), user.getUsername());
    }

}
