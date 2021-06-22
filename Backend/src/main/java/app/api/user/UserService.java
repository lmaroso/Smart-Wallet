package app.api.user;

import app.api.expense.ExpenseRepository;
import app.api.income.IncomeRepository;
import app.api.token.ConfirmationTokenRepository;
import app.api.token.ConfirmationTokenService;
import app.model.Email.Email;
import app.model.Email.Sender;
import app.model.Exceptions.*;
import app.model.Expense.Expense;
import app.model.Income.Income;
import app.model.Task.ExpenseTask;
import app.model.Task.IncomeTask;
import app.model.Token.ConfirmationToken;
import app.model.User.User;
import app.model.Validators.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.Timer;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User %s not found";
    private final static String ID_NOT_FOUND = "Id %s not found";

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final IncomeRepository incomeRepository;
    @Autowired
    private final ExpenseRepository expenseRepository;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    //Constructor
    public UserService(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository,
                       IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.emailValidator = new EmailValidator();
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.confirmationTokenService = new ConfirmationTokenService(confirmationTokenRepository, userRepository);
    }

    //Methods
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.findUserByEmail(email);
    }

    public void registerUser(User user) {
        boolean isValidEmail = this.emailValidator.test(user.getUsername());
        if (!isValidEmail){
            throw new InvalidEmailException(user.getUsername());
        }
        this.signUpUser(user);
    }

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(ID_NOT_FOUND, id)));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public void signUpUser(User user){

        boolean userExists = userRepository.findByEmail(user.getUsername()).isPresent();
        if(userExists){
            throw new UsedEmailException(user.getUsername());
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token  = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                                                                    LocalDateTime.now(),
                                                                    LocalDateTime.now().plusMinutes(15),
                                                                    user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        Sender sender = new Sender("Client send", this.buildConfirmEmail(user.getUsername(), user.getName(), token));
        sender.start();
    }

    private Email buildConfirmEmail(String reciver, String name, String token){
        Email email = new Email();
        String link = "http://localhost:8080/register/confirm?token=" + token;

        email.setReceiver(reciver);
        email.setSubject("Confirm your email");
        email.setMessage(this.buildConfirmEmailMessage(name, link));

        return email;
    }

    public void createExpenseTask(Expense expense){

        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        LocalDateTime now = LocalDateTime.now();
        int repetitionMilliSeconds = expense.getRepetitionMilliSeconds();
        LocalDateTime date = expense.getDate();

        if (repetitionMilliSeconds == 0){
            //Si no se especifican los segundos, la tarea se ejecuta cada 24 hs en caso de estar programada.
            repetitionMilliSeconds = 86400000;
        }

        if(date.isAfter(now)) {
            calendar.set(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(),
                    date.getMinute(), date.getSecond());

            timer.schedule(new ExpenseTask(expense.getId(), expenseRepository, userRepository),
                    calendar.getTime(),
                    repetitionMilliSeconds);
        }
        else{
            timer.schedule(new ExpenseTask(expense.getId(), expenseRepository, userRepository),
                    0,
                    repetitionMilliSeconds);
        }

    }

    public void createIncomeTask(Income income){

        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        LocalDateTime now = LocalDateTime.now();
        int repetitionMilliSeconds = income.getRepetitionMilliSeconds();
        LocalDateTime date = income.getDate();

        if (repetitionMilliSeconds == 0){
            //Si no se especifican los segundos, la tarea se ejecuta cada 24 hs en caso de estar programada.
            repetitionMilliSeconds = 86400000;
        }

        if(date.isAfter(now)) {
            calendar.set(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(),
                    date.getMinute(), date.getSecond());

            timer.schedule(new IncomeTask(income.getId(), incomeRepository, userRepository),
                    calendar.getTime(),
                    repetitionMilliSeconds);
        }
        else{
            timer.schedule(new IncomeTask(income.getId(), incomeRepository, userRepository),
                    0,
                    repetitionMilliSeconds);
        }

    }

    public void updateAccountCredit(long userId, Double amount){
        userRepository.updateAccountCredit(amount, userId);
    }

    public void updateAccountExpense(long userId, Double amount){ userRepository.updateAccountExpense(amount, userId); }

    public void enableUser(String email){
        userRepository.enableAppUser(email);
    }

    public void updateProfile(Long id, String name, String email) {

        boolean isValidEmail = this.emailValidator.test(email);
        if (!isValidEmail){
            throw new InvalidEmailException(email);
        }

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent() && user.get().getId() != id){
            throw new UsedEmailException(email);
        }

        User userEdit = findUserById(id);
        userEdit.setName(name);
        userEdit.setEmail(email);

        userRepository.save(userEdit);

    }

    public double getBalance(Long id) {
        User user = findUserById(id);
        return (user.getAccountCredit() - user.getAccountExpense());
    }

    private String buildConfirmEmailMessage(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }



}
