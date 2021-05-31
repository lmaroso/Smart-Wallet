package integationTests;

import app.SmartWalletApplication;
import app.api.token.ConfirmationTokenService;
import app.api.user.UserRepository;
import app.api.user.UserService;
import app.dto.ExpenseDTO;
import app.dto.IncomeDTO;
import app.dto.LoginDTO;
import app.dto.UserDTO;
import app.model.User.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SmartWalletApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    private String smart1Token;

    private String smart2Token;

    @Before
    public void setup() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        //Registra usuarios para tests.
        UserDTO smart1RegisterUser  = new UserDTO("Smart1", "smart.wallet.app1@gmail.com", "sw");
        UserDTO smart2RegisterUser  = new UserDTO("Smart2", "smart.wallet.app2@gmail.com", "sw");
        UserDTO smart3RegisterUser  = new UserDTO("Smart3", "smart.wallet.app3@gmail.com", "sw");

        String smart1RegisterJsonRequest  = mapper.writeValueAsString(smart1RegisterUser);
        String smart2RegisterJsonRequest  = mapper.writeValueAsString(smart2RegisterUser);
        String smart3RegisterJsonRequest  = mapper.writeValueAsString(smart3RegisterUser);

        mockMvc.perform(post("/register").content(smart1RegisterJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(post("/register").content(smart2RegisterJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(post("/register").content(smart3RegisterJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Habilita usuarios.
        userService.enableUser("smart.wallet.app1@gmail.com");
        userService.enableUser("smart.wallet.app2@gmail.com");

        //Loguea usuarios para utilizar en tests.
        LoginDTO smart1LoginUser = new LoginDTO();
        smart1LoginUser.setUsername("smart.wallet.app1@gmail.com");
        smart1LoginUser.setPassword("sw");

        LoginDTO smart2LoginUser = new LoginDTO();
        smart2LoginUser.setUsername("smart.wallet.app2@gmail.com");
        smart2LoginUser.setPassword("sw");

        String smart1LoginJsonRequest = mapper.writeValueAsString(smart1LoginUser);
        String smart2LoginJsonRequest = mapper.writeValueAsString(smart2LoginUser);

        //Realiza logins y obtiene tokens.
        smart1Token = mockMvc.perform(post("/login").content(smart1LoginJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getHeader("Authorization");

        smart2Token = mockMvc.perform(post("/login").content(smart2LoginJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getHeader("Authorization");

        //Agrega un Gasto
        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000, LocalDateTime.now(), true);
        String jsonRequestExpense = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart1Token)
                .content(jsonRequestExpense).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testConfirmUser() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app3@gmail.com");
        String userToken = confirmationTokenService.getTokenByUser(user);

        mockMvc.perform(get("/register/confirm?token=" + userToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testSuccessRegister() throws Exception {

        UserDTO user = new UserDTO("Fede", "fedejmartinez@smartwallet.com", "f");
        String jsonRequest = mapper.writeValueAsString(user);

        mockMvc.perform(post("/register").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testRegisterWithUsedEmail() throws Exception {

        UserDTO user = new UserDTO("Smart2", "smart.wallet.app2@gmail.com", "sw");
        String jsonRequest = mapper.writeValueAsString(user);

        mockMvc.perform(post("/register")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    public void testRegisterWithInvalidMail() throws Exception {

        UserDTO user = new UserDTO("Jose", "jose.com", "f");
        String jsonRequest = mapper.writeValueAsString(user);

        mockMvc.perform(post("/register").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    public void testSuccessLogin() throws Exception {

        LoginDTO loginUser = new LoginDTO();
        loginUser.setUsername("smart.wallet.app2@gmail.com");
        loginUser.setPassword("sw");

        String loginJsonRequest = mapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/login")
                .content(loginJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testIncorrectPasswordLogin() throws Exception {

        LoginDTO loginUser = new LoginDTO();
        loginUser.setUsername("smart.wallet.app1@gmail.com");
        loginUser.setPassword("incorrect_password");

        String loginJsonRequest = mapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/login")
                .content(loginJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()).andReturn();

    }

    @Test
    public void testIncorrectUsernameLogin() throws Exception {

        LoginDTO loginUser = new LoginDTO();
        loginUser.setUsername("incorrect_email@gmail.com");
        loginUser.setPassword("sw");

        String loginJsonRequest = mapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/login")
                .content(loginJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();

    }

    @Test
    public void testProfile() throws Exception{

        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app2@gmail.com").getId());

        mockMvc.perform(get("/getProfile/" + id)
                .header("Authorization", smart2Token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testSuccessAddIncome() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);
        String jsonRequest = mapper.writeValueAsString(income);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testIncorrectAddIncome() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 0, LocalDateTime.now(), false);
        String jsonRequest = mapper.writeValueAsString(income);

        MvcResult result = mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertEquals("Amount is not allow", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());

    }


    @Test
    public void testSuccessAddExpense() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000, LocalDateTime.now(), true);
        String jsonRequest = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testIncorrectAddExpense() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 0, LocalDateTime.now(), true);
        String jsonRequest = mapper.writeValueAsString(expense);

        MvcResult result = mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertEquals("Amount is not allow", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());

    }

    @Test
    public void testSuccessGetExpense() throws Exception{

        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app1@gmail.com").getId());

        MvcResult result = mockMvc.perform(get("/getExpense/" + id)
                .header("Authorization", smart1Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        //assertEquals("Not found expense", result.getResolvedException().getMessage());
       assertEquals( 200, result.getResponse().getStatus());
    }

    @Test
    public void testIncorrectGetExpense() throws Exception{

        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app2@gmail.com").getId());

        MvcResult result = mockMvc.perform(get("/getExpense/" + id)
                .header("Authorization", smart2Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Not found expense", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());
    }

    @Test
    public void testRegisterLoginAndIncome() throws Exception{

        UserDTO smart4RegisterUser  = new UserDTO("Smart4", "smart.wallet.app4@gmail.com", "sw");
        String smart4RegisterJsonRequest  = mapper.writeValueAsString(smart4RegisterUser);

        //El usuario se registra.
        mockMvc.perform(post("/register").content(smart4RegisterJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        User user = userService.findUserByEmail("smart.wallet.app4@gmail.com");
        String userToken = confirmationTokenService.getTokenByUser(user);

        //El usuario confirma su token.
        mockMvc.perform(get("/register/confirm?token=" + userToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        LoginDTO loginUser = new LoginDTO();
        loginUser.setUsername("smart.wallet.app4@gmail.com");
        loginUser.setPassword("sw");

        String loginJsonRequest = mapper.writeValueAsString(loginUser);

        //El usuario se loguea
        String smart4Token = mockMvc.perform(post("/login").content(loginJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getHeader("Authorization");

        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);
        String incomeJsonRequest = mapper.writeValueAsString(income);

        //El usuario agrega un ingreso.
        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart4Token)
                .content(incomeJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        User updatedUser = userService.findUserById(user.getId());

        assertEquals(35000, updatedUser.getAccountCredit(), 0);

        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000, LocalDateTime.now(), true);
        String expenseJsonRequest = mapper.writeValueAsString(expense);

        //El usuario agrega un gasto.
        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart4Token)
                .content(expenseJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        updatedUser = userService.findUserById(user.getId());

        assertEquals(20000, updatedUser.getAccountExpense(), 0);

    }

}