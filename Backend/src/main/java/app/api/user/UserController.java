package app.api.user;

import app.dto.ProfileDTO;
import app.dto.UserDTO;
import app.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {

    private final static String ID_NOT_FOUND = "Id %s not found";

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

        Long longID = Long.valueOf(0);

        try {
            longID = Long.parseLong(id);
        }
        catch (Exception e){
            new UsernameNotFoundException(String.format(ID_NOT_FOUND, id));
        }
        User user = userService.findUserById(longID);
        return new ProfileDTO(user.getName(), user.getUsername());

    }

    //Post
    @PostMapping(value="/edit/{id}")
    public void editProfile(@PathVariable("id") String id, @RequestBody ProfileDTO profileDTO){
        Long longID = Long.valueOf(0);

        try {
            longID = Long.parseLong(id);
        }
        catch (Exception e){
            new UsernameNotFoundException(String.format(ID_NOT_FOUND, id));
        }
        userService.updateProfile(longID, profileDTO.getName(), profileDTO.getEmail());
    }

    //Get
    @GetMapping(value = "/balance/{id}")
    public double balance(@PathVariable("id") String id){
        Long longID = Long.valueOf(0);

        try {
            longID = Long.parseLong(id);
        }
        catch (Exception e){
            new UsernameNotFoundException(String.format(ID_NOT_FOUND, id));
        }
        return userService.getBalance(longID);
    }
}
