package app.api.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "register")
public class ConfirmationTokenController {

    @Autowired
    private final ConfirmationTokenService confirmationTokenService;

    //Constructor
    public ConfirmationTokenController(ConfirmationTokenService confirmationTokenService){
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return confirmationTokenService.confirmToken(token);
    }

}
