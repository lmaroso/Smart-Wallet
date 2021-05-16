package app.api.user;

import app.api.income.IncomeService;
import app.dto.UserDTO;
import app.dto.IncomeDTO;
import app.model.User.Income;
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

    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    public User findUserNamed(@PathVariable("name") String name) {
        return userService.findUserNamed(name);
    }

}
