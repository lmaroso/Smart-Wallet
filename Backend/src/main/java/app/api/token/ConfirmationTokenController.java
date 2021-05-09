package app.api.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class ConfirmationTokenController {

    @Autowired
    private final ConfirmationTokenService confirmationTokenService;

    //Constructor
    public ConfirmationTokenController(ConfirmationTokenService confirmationTokenService){
        this.confirmationTokenService = confirmationTokenService;
    }

    @PostMapping(path = "/register/confirm")
    public String confirm(@RequestParam("token") String token) {
        return confirmationTokenService.confirmToken(token);
    }

}
