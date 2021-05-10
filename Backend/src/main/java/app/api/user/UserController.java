package app.api.user;

import app.dto.LoginDTO;
import app.dto.UserDTO;
import app.dto.IncomeDTO;
import app.model.Exceptions.InvalidUserPassException;
import app.model.User.Income;
import app.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IncomeService incomeService;

    //Post
    @PostMapping(value = "/register")
    public String registerUser(@RequestBody UserDTO userDTO) {

        User user = new User(userDTO.name, userDTO.email, userDTO.password);
        return userService.registerUser(user);
    }

    @PostMapping(value = "/login")
    public void loginUser(@RequestBody LoginDTO loginDTO) {

        User user;

        //Chequeo nombre de usuario.
        try {
            user = userService.findUserByEmail(loginDTO.email);
        }
        catch (UsernameNotFoundException e) {
            throw new InvalidUserPassException();
        }

        //Chequeo password.
        if(!loginDTO.pass.equals(user.getPassword())){
            throw new InvalidUserPassException();
        }

    }

    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    public User findUserNamed(@PathVariable("name") String name) {
        return userService.findUserNamed(name);
    }

    @PostMapping(value = "/addIncome")
    public void addIncome(@RequestBody IncomeDTO incomeDTO) {
        Income income = new Income(incomeDTO.amount, incomeDTO.date, incomeDTO.programmed);
        incomeService.saveIncome(income);
    }

}
