package app.api.token;

import app.api.user.UserRepository;
import app.api.user.UserService;
import app.model.Exceptions.EmailAlreadyConfirmedException;
import app.model.Exceptions.TokenExpiredException;
import app.model.Exceptions.TokenNotFoundException;
import app.model.Token.ConfirmationToken;
import app.model.User.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;

    //Constructor
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository){
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
    }

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }

    public String getTokenByUser(User user){
        return confirmationTokenRepository.findByUser(user).getToken();
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = this
                .getToken(token)
                .orElseThrow(() -> new TokenNotFoundException());

        if(confirmationToken.getConfirmedAt() != null){
            throw new EmailAlreadyConfirmedException();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())){
            throw new TokenExpiredException();
        }

        this.setConfirmedAt(token);
        this.enableAppUser(confirmationToken.getUser().getUsername());

        return "Confirmed";
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

}
