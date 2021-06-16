package integationTests;

import app.SmartWalletApplication;
import app.api.expense.ExpenseService;
import app.api.income.IncomeService;
import app.api.token.ConfirmationTokenService;
import app.api.user.UserRepository;
import app.api.user.UserService;
import app.dto.*;
import app.model.Expense.Expense;
import app.model.Income.Income;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    private ExpenseService expenseService;

    @Autowired
    private IncomeService incomeService;

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

        //Obtengo un usuario.
        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");

        //Agrega un gasto.
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequestExpense = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart1Token)
                .content(jsonRequestExpense).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //Agrega un ingreso.
        IncomeDTO income = new IncomeDTO(user.getId(),"Sueldo", "Sueldo mensual", 90000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequestIncome = mapper.writeValueAsString(income);
        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart1Token)
                .content(jsonRequestIncome).contentType(MediaType.APPLICATION_JSON))
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
    public void testLoginWithIncorrectPassword() throws Exception {

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
    public void testLoginWithIncorrectUsername() throws Exception {

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
    public void testSuccessGetProfile() throws Exception{

        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app2@gmail.com").getId());

        mockMvc.perform(get("/getProfile/" + id)
                .header("Authorization", smart2Token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testSuccessEditProfile() throws Exception{

        String id =  String.valueOf(userService.findUserByEmail("smart.wallet.app2@gmail.com").getId());
        ProfileDTO profileDTO = new ProfileDTO("S2", "smart.wallet.app@gmail.com");

        String jsonRequest = mapper.writeValueAsString(profileDTO);

        mockMvc.perform(post("/edit/" + id)
                .header("Authorization", smart2Token)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testEditProfileWithUsedEmail() throws Exception{

        String id =  String.valueOf(userService.findUserByEmail("smart.wallet.app2@gmail.com").getId());
        ProfileDTO profileDTO = new ProfileDTO("S2", "smart.wallet.app1@gmail.com");

        String jsonRequest = mapper.writeValueAsString(profileDTO);

        mockMvc.perform(post("/edit/" + id)
                .header("Authorization", smart2Token)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void testEditProfileWithIncorrectEmail() throws Exception{

        String id =  String.valueOf(userService.findUserByEmail("smart.wallet.app2@gmail.com").getId());
        ProfileDTO profileDTO = new ProfileDTO("S2", "incorrect_email.com");

        String jsonRequest = mapper.writeValueAsString(profileDTO);

        mockMvc.perform(post("/edit/" + id)
                .header("Authorization", smart2Token)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void testSuccessAddIncome() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(income);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<Income> incomes = incomeService.getIncomeHistory(String.valueOf(user.getId()));

        assertEquals(1, incomes.size());

    }

    @Test
    public void testInvalidProgrammedIncome() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, true, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(income);

        MvcResult result = mockMvc.perform(post("/addIncome")
                                  .header("Authorization", smart2Token)
                                  .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                                  .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Programmed values are not valid", result.getResolvedException().getMessage());

    }

    public void testSuccessAddProgrammedIncome() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, true, 2000, 0, 0);
        String jsonRequest = mapper.writeValueAsString(income);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<Income> incomes = incomeService.getIncomeHistory(String.valueOf(user.getId()));

        Thread.sleep(2500);

        long idIncome  = incomeService.getIncomeHistory(String.valueOf(user.getId())).get(0).getId();
        income = new IncomeDTO(idIncome, user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, true, 2000, 0, 0);
        jsonRequest = mapper.writeValueAsString(income);

        mockMvc.perform(post("/editIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        User updatedUser = userService.findUserById(user.getId());

        assertEquals(1, incomes.size());
        assertEquals(70000.0, updatedUser.getAccountCredit(), 0);

    }

    @Test
    public void testSuccessAdd2Incomes() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income1 = new IncomeDTO(user.getId(),"Sueldo", "Sueldo mensual", 80000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        IncomeDTO income2 = new IncomeDTO(user.getId(),"Extra", "Horas extras", 2000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest1 = mapper.writeValueAsString(income1);
        String jsonRequest2 = mapper.writeValueAsString(income2);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<Income> incomes = incomeService.getIncomeHistory(String.valueOf(user.getId()));

        Thread.sleep(1000);

        User updatedUser = userService.findUserById(user.getId());

        assertEquals(2, incomes.size());
        assertEquals(82000, updatedUser.getAccountCredit(), 0);

    }

    @Test
    public void testFilteredIncomes() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO income1 = new ExpenseDTO(user.getId(),"Sueldo", "Sueldo mensual", 80000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        ExpenseDTO income2 = new ExpenseDTO(user.getId(),"Extra", "Horas extras", 2000.0, LocalDateTime.now().minusDays(1), false, false, 0, 0, 0);
        String jsonRequest1 = mapper.writeValueAsString(income1);
        String jsonRequest2 = mapper.writeValueAsString(income2);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<Income> incomes = incomeService.getIncomeHistory(String.valueOf(user.getId()),
                                                              LocalDateTime.now().minusMinutes(15),
                                                              LocalDateTime.now().plusMinutes(15));

        assertEquals(1, incomes.size());

    }

    @Test
    public void testSuccessFilteredIncomesEndpoint() throws Exception{

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");
        IncomeDTO income1 = new IncomeDTO(user.getId(),"Sueldo", "Sueldo mensual", 80000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        IncomeDTO income2 = new IncomeDTO(user.getId(),"Extra", "Horas extras", 2000.0, LocalDateTime.now().minusDays(1), false, false, 0, 0, 0);
        String jsonRequest1 = mapper.writeValueAsString(income1);
        String jsonRequest2 = mapper.writeValueAsString(income2);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart1Token)
                .content(jsonRequest1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart1Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String from = LocalDateTime.now().minusDays(1).format(fmt);
        String to = LocalDateTime.now().plusDays(2).format(fmt);

        MvcResult result = mockMvc.perform(get("/getIncomeHistory/" + user.getId() + "/" + from + "/" + to)
                .header("Authorization", smart1Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals( 200, result.getResponse().getStatus());

    }

    @Test
    public void testSuccessFilteredIncomesEndpointInvalidDateException() throws Exception{

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");
        IncomeDTO income1 = new IncomeDTO(user.getId(),"Sueldo", "Sueldo mensual", 80000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        IncomeDTO income2 = new IncomeDTO(user.getId(),"Extra", "Horas extras", 2000.0, LocalDateTime.now().minusDays(1), false, false, 0, 0, 0);
        String jsonRequest1 = mapper.writeValueAsString(income1);
        String jsonRequest2 = mapper.writeValueAsString(income2);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart1Token)
                .content(jsonRequest1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart1Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String from = "Invalid date";
        String to = LocalDateTime.now().plusDays(2).format(fmt);

        MvcResult result = mockMvc.perform(get("/getIncomeHistory/" + user.getId() + "/" + from + "/" + to)
                .header("Authorization", smart1Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Invalid date", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());

    }

    @Test
    public void testIncorrectAddIncome() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 0.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(income);

        MvcResult result = mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertEquals("Amount is not allowed", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());

    }

    @Test
    public void testSuccessEditIncome() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");

        long idIncome  = incomeService.getIncomeHistory(String.valueOf(user.getId())).get(0).getId();
        IncomeDTO income = new IncomeDTO(idIncome, user.getId(),
                "Sueldo", "Sueldo mensual", 40000.0, LocalDateTime.now(),
                false, false,0, 0, 0);

        String jsonRequest = mapper.writeValueAsString(income);

        mockMvc.perform(post("/editIncome")
                .header("Authorization", smart1Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Thread.sleep(1000);

        Double finalAmountIncome  = incomeService.getIncomeHistory(String.valueOf(user.getId())).get(0).getAmount();
        Double expected = 40000.0;

        double finalAccountCredit = userService.findUserByEmail("smart.wallet.app1@gmail.com").getAccountCredit();

        assertEquals(expected, finalAmountIncome);
        assertTrue(40000.0 == finalAccountCredit);
    }

    @Test
    public void testNotFoundIncomeToEdit() throws Exception{
        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");
        long idRandom = 38;

        IncomeDTO income = new IncomeDTO(idRandom, user.getId(),
                "Sueldo", "Sueldo mensual", 40000.0, LocalDateTime.now(),
                false, false, 0, 0, 0);

        String jsonRequest = mapper.writeValueAsString(income);

        MvcResult result = mockMvc.perform(post("/editIncome")
                .header("Authorization", smart1Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Not found income", result.getResolvedException().getMessage());
    }

    @Test
    public void testSuccessDeleteIncome() throws Exception{
        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 40000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(income);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Income addIncome = incomeService.getIncomeHistory(Long.toString(user.getId())).get(0);
        String incomeId = Long.toString(addIncome.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteIncome/" + incomeId)
                .header("Authorization", smart2Token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Integer expected = 0;
        Integer actual = incomeService.getIncomeHistory(Long.toString(user.getId())).size();

        assertEquals(expected, actual);

    }

    @Test
    public void testSuccessDeleteIncomeBetween2() throws Exception{
        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 40000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(income);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        IncomeDTO income2 = new IncomeDTO(user.getId(), "Hs Extras", "6 hs", 4000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest2 = mapper.writeValueAsString(income2);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Income addIncome = incomeService.getIncomeHistory(Long.toString(user.getId())).get(0);
        String incomeId = Long.toString(addIncome.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteIncome/" + incomeId)
                .header("Authorization", smart2Token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Integer expected = 1;
        Integer actual = incomeService.getIncomeHistory(Long.toString(user.getId())).size();

        assertEquals(expected, actual);

    }

    @Test
    public void testIncorrectIdIncomeToDelete() throws Exception{
        String idRandom = "105";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/deleteIncome/" + idRandom)
                .header("Authorization", smart2Token)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(result.getResolvedException().getMessage(), "Not found income");
        assertEquals(result.getResponse().getStatus(),  400);
    }

    @Test
    public void testSuccessGetIncomeHistory() throws Exception{

        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app1@gmail.com").getId());

        MvcResult result = mockMvc.perform(get("/getIncomeHistory/" + id)
                .header("Authorization", smart1Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals( 200, result.getResponse().getStatus());

    }

    @Test
    public void testIncorrectGetIncomeHistory() throws Exception{

        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app2@gmail.com").getId());

        MvcResult result = mockMvc.perform(get("/getIncomeHistory/" + id)
                .header("Authorization", smart2Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Not found income", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());
    }

    @Test
    public void testSuccessAddExpense() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<Expense> expenses = expenseService.getExpenseHistory(String.valueOf(user.getId()));

        assertEquals(1, expenses.size());

    }

    @Test
    public void testInvalidProgrammedExpense() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense = new ExpenseDTO(user.getId(), "Alquiler", "Alquiler mensual", 35000.0, LocalDateTime.now(), false, true, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(expense);

        MvcResult result = mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Programmed values are not valid", result.getResolvedException().getMessage());

    }

    @Test
    public void testSuccessAddProgrammedExpense() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense = new ExpenseDTO(user.getId(), "Alquiler", "Alquiler mensual", 35000.0, LocalDateTime.now(), false, true, 2000, 0, 0);
        String jsonRequest = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<Expense> expenses = expenseService.getExpenseHistory(String.valueOf(user.getId()));

        Thread.sleep(2500);

        long idExpense  = expenseService.getExpenseHistory(String.valueOf(user.getId())).get(0).getId();
        expense = new ExpenseDTO(idExpense, user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, true, 2000, 0, 0);
        jsonRequest = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/editExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        User updatedUser = userService.findUserById(user.getId());

        assertEquals(1, expenses.size());
        assertEquals(70000.0, updatedUser.getAccountExpense(), 0);

    }

    @Test
    public void testSuccessAdd3Expenses() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense1 = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        ExpenseDTO expense2 = new ExpenseDTO(user.getId(),"Supermercado", "Compra mensual", 7000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        ExpenseDTO expense3 = new ExpenseDTO(user.getId(),"Club", "Cuota social", 3500.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest1 = mapper.writeValueAsString(expense1);
        String jsonRequest2 = mapper.writeValueAsString(expense2);
        String jsonRequest3 = mapper.writeValueAsString(expense3);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest3).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<Expense> expenses = expenseService.getExpenseHistory(String.valueOf(user.getId()));

        Thread.sleep(1000);

        User updatedUser = userService.findUserById(user.getId());

        assertEquals(3, expenses.size());
        assertEquals(30500, updatedUser.getAccountExpense(), 0);

    }

    @Test
    public void testAddExpenseWithIncorrectAmount() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 0.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(expense);

        MvcResult result = mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertEquals("Amount is not allowed", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());

    }

    @Test
    public void testSuccessEditExpense() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");

        long idExpense  = expenseService.getExpenseHistory(String.valueOf(user.getId())).get(0).getId();
        ExpenseDTO expense = new ExpenseDTO(idExpense, user.getId(),"Alquiler", "Alquiler mensual", 30000.0, LocalDateTime.now(), false, false, 0, 0, 0);

        String jsonRequest = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/editExpense")
                .header("Authorization", smart1Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Double finalAmountExpense  = expenseService.getExpenseHistory(String.valueOf(user.getId())).get(0).getAmount();
        Double expected = 30000.0;

        double finalAccountExpense = userService.findUserByEmail("smart.wallet.app1@gmail.com").getAccountExpense();

        assertEquals(expected, finalAmountExpense);
        assertTrue(30000.0 == finalAccountExpense);
    }

    @Test
    public void testNotFoundExpenseToEdit() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");
        long idRandom = 3555;

        ExpenseDTO expense = new ExpenseDTO(idRandom, user.getId(),
                "Sueldo", "Sueldo mensual", 40000.0, LocalDateTime.now(),
                false, false, 0, 0, 0);

        String jsonRequest = mapper.writeValueAsString(expense);

        MvcResult result = mockMvc.perform(post("/editExpense")
                .header("Authorization", smart1Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Not found expense", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());

    }

    @Test
    public void testSuccessDeleteExpense() throws Exception{
        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Expense addExpense = expenseService.getExpenseHistory(Long.toString(user.getId())).get(0);
        String expenseId = Long.toString(addExpense.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteExpense/" + expenseId)
                .header("Authorization", smart2Token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Integer expected = 0;
        Integer actual = expenseService.getExpenseHistory(Long.toString(user.getId())).size();

        assertEquals(expected, actual);

    }

    @Test
    public void testSuccessDeleteExpenseBetween2() throws Exception{
        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        ExpenseDTO expense2 = new ExpenseDTO(user.getId(),"Luz", "Luz eléctrica", 1000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String jsonRequest2 = mapper.writeValueAsString(expense2);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart2Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Expense addExpense = expenseService.getExpenseHistory(Long.toString(user.getId())).get(0);
        String expenseId = Long.toString(addExpense.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteExpense/" + expenseId)
                .header("Authorization", smart2Token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Integer expected = 1;
        Integer actual = expenseService.getExpenseHistory(Long.toString(user.getId())).size();

        assertEquals(expected, actual);

    }

    @Test
    public void testIncorrectIdExpenseToDelete() throws Exception{
       String idRandom = "105";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/deleteExpense/" + idRandom)
                .header("Authorization", smart2Token)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(result.getResolvedException().getMessage(), "Not found expense");
        assertEquals(result.getResponse().getStatus(),  400);
    }

    @Test
    public void testSuccessGetExpenseHistory() throws Exception{

        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app1@gmail.com").getId());

        MvcResult result = mockMvc.perform(get("/getExpenseHistory/" + id)
                .header("Authorization", smart1Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

       assertEquals( 200, result.getResponse().getStatus());

    }

    @Test
    public void testIncorrectGetExpenseHistory() throws Exception{

        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app2@gmail.com").getId());

        MvcResult result = mockMvc.perform(get("/getExpenseHistory/" + id)
                .header("Authorization", smart2Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Not found expense", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());

    }

    @Test
    public void testFilteredExpenses() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        ExpenseDTO expense1 = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 80000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        ExpenseDTO expense2 = new ExpenseDTO(user.getId(),"Supermercado", "Compra mensual", 2000.0, LocalDateTime.now().minusDays(1), false, false, 0, 0, 0);
        String jsonRequest1 = mapper.writeValueAsString(expense1);
        String jsonRequest2 = mapper.writeValueAsString(expense2);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<Income> incomes = incomeService.getIncomeHistory(String.valueOf(user.getId()),
                LocalDateTime.now().minusMinutes(15),
                LocalDateTime.now().plusMinutes(15));

        assertEquals(1, incomes.size());

    }

    @Test
    public void testSuccessFilteredExpensesEndpoint() throws Exception{

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");
        ExpenseDTO expense1 = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 80000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        ExpenseDTO expense2 = new ExpenseDTO(user.getId(),"Supermercado", "Compra mensual", 2000.0, LocalDateTime.now().minusDays(1), false, false, 0, 0, 0);
        String jsonRequest1 = mapper.writeValueAsString(expense1);
        String jsonRequest2 = mapper.writeValueAsString(expense2);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart1Token)
                .content(jsonRequest1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart1Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String from = LocalDateTime.now().minusDays(1).format(fmt);
        String to = LocalDateTime.now().plusDays(2).format(fmt);

        MvcResult result = mockMvc.perform(get("/getExpenseHistory/" + user.getId() + "/" + from + "/" + to)
                .header("Authorization", smart1Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals( 200, result.getResponse().getStatus());

    }

    @Test
    public void testSuccessFilteredExpensesEndpointInvalidDateException() throws Exception{

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        User user = userService.findUserByEmail("smart.wallet.app1@gmail.com");
        ExpenseDTO expense1 = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 80000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        ExpenseDTO expense2 = new ExpenseDTO(user.getId(),"Supermercado", "Compra mensual", 2000.0, LocalDateTime.now().minusDays(1), false, false, 0, 0, 0);
        String jsonRequest1 = mapper.writeValueAsString(expense1);
        String jsonRequest2 = mapper.writeValueAsString(expense2);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart1Token)
                .content(jsonRequest1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart1Token)
                .content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String from = "Invalid date";
        String to = LocalDateTime.now().plusDays(2).format(fmt);

        MvcResult result = mockMvc.perform(get("/getExpenseHistory/" + user.getId() + "/" + from + "/" + to)
                .header("Authorization", smart1Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Invalid date", result.getResolvedException().getMessage());
        assertEquals( 400, result.getResponse().getStatus());

    }

    @Test
    public void testSuccessGetBalance() throws Exception{

        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app1@gmail.com").getId());

        MvcResult result = mockMvc.perform(get("/balance/" + id)
                .header("Authorization", smart1Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        Long idLong = userService.findUserByEmail("smart.wallet.app1@gmail.com").getId();
        double actual = userService.getBalance(idLong);
        double expected = 70000.0;

        assertEquals( 200, result.getResponse().getStatus());
        assertTrue(expected == actual);

    }

    @Test
    public void testNotFoundUserToGetBalance() throws Exception{

        String idRandom = "20";

        MvcResult result = mockMvc.perform(get("/balance/" + idRandom)
                .header("Authorization", smart1Token)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals("Access Denied", result.getResponse().getErrorMessage());
        assertEquals( 403, result.getResponse().getStatus());

    }

    @Test
    public void testRegisterLoginAddIncomeAndAddExpense() throws Exception{

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

        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String incomeJsonRequest = mapper.writeValueAsString(income);

        //El usuario agrega un ingreso.
        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart4Token)
                .content(incomeJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Thread.sleep(1000);

        User updatedUser = userService.findUserById(user.getId());

        assertEquals(35000, updatedUser.getAccountCredit(), 0);

        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String expenseJsonRequest = mapper.writeValueAsString(expense);

        //El usuario agrega un gasto.
        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart4Token)
                .content(expenseJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Thread.sleep(1000);

        updatedUser = userService.findUserById(user.getId());

        assertEquals(20000, updatedUser.getAccountExpense(), 0);

    }

    @Test
    public void testUserRegisterLoginAndEditProfile() throws Exception{

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

        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String incomeJsonRequest = mapper.writeValueAsString(income);

        //El usuario consulta su perfil
        String id = String.valueOf(userService.findUserByEmail("smart.wallet.app4@gmail.com").getId());

        mockMvc.perform(get("/getProfile/" + id)
                .header("Authorization", smart2Token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //El usuario edita su perfil
        ProfileDTO profileDTO = new ProfileDTO("S2", "smart.wallet.app@gmail.com");

        String jsonRequest = mapper.writeValueAsString(profileDTO);

        mockMvc.perform(post("/edit/" + id)
                .header("Authorization", smart2Token)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        User editProfileUser = userService.findUserById(user.getId());

        assertEquals(editProfileUser.getUsername() , "smart.wallet.app@gmail.com");
        assertEquals(editProfileUser.getName(), "S2");

    }

    @Test
    public void testRegisterLoginAddIncomeAddExpenseAndGetBalance() throws Exception{
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

        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String incomeJsonRequest = mapper.writeValueAsString(income);

        //El usuario agrega un ingreso.
        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart4Token)
                .content(incomeJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Thread.sleep(1000);

        User updatedUser = userService.findUserById(user.getId());

        assertEquals(35000, updatedUser.getAccountCredit(), 0);

        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String expenseJsonRequest = mapper.writeValueAsString(expense);

        //El usuario agrega un gasto.
        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart4Token)
                .content(expenseJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Thread.sleep(1000);

        updatedUser = userService.findUserById(user.getId());

        double expectedBalance = 15000.0;
        double actualBalance = userService.getBalance(user.getId());

        assertEquals(20000, updatedUser.getAccountExpense(), 0);

        assertTrue(expectedBalance == actualBalance);
    }

    @Test
    public void testRegisterLoginAddIncomeAdd2ExpensesAndGetBalance() throws Exception{
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

        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String incomeJsonRequest = mapper.writeValueAsString(income);

        //El usuario agrega un ingreso.
        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart4Token)
                .content(incomeJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Thread.sleep(1000);

        User updatedUser = userService.findUserById(user.getId());

        assertEquals(35000, updatedUser.getAccountCredit(), 0);

        //El usuario agrega un gasto.
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String expenseJsonRequest = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart4Token)
                .content(expenseJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //El usuario agrega un segundo gasto.
        ExpenseDTO expense2 = new ExpenseDTO(user.getId(),"Luz", "Luz mensual", 1200.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String expense2JsonRequest = mapper.writeValueAsString(expense2);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart4Token)
                .content(expense2JsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Thread.sleep(1000);

        updatedUser = userService.findUserById(user.getId());

        double expectedBalance = 13800.0;
        double actualBalance = userService.getBalance(user.getId());

        assertEquals(21200, updatedUser.getAccountExpense(), 0);

        assertTrue(expectedBalance == actualBalance);

    }

    @Test
    public void testRegisterLoginAddIncomeAddExpensesEditExpenseAndGetBalance() throws Exception{
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

        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String incomeJsonRequest = mapper.writeValueAsString(income);

        //El usuario agrega un ingreso.
        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart4Token)
                .content(incomeJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Thread.sleep(1000);

        User updatedUser = userService.findUserById(user.getId());

        assertEquals(35000, updatedUser.getAccountCredit(), 0);

        //El usuario agrega un gasto.
        ExpenseDTO expense = new ExpenseDTO(user.getId(),"Alquiler", "Alquiler mensual", 20000.0, LocalDateTime.now(), false, false, 0, 0, 0);
        String expenseJsonRequest = mapper.writeValueAsString(expense);

        mockMvc.perform(post("/addExpense")
                .header("Authorization", smart4Token)
                .content(expenseJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //El usuario edita el gasto agregado anteriormente.
        long idExpense  = expenseService.getExpenseHistory(String.valueOf(user.getId())).get(0).getId();
        ExpenseDTO expenseEdit = new ExpenseDTO(idExpense, user.getId(),"Alquiler", "Alquiler mensual", 30000.0, LocalDateTime.now(), false, false, 0, 0, 0);

        String jsonRequest = mapper.writeValueAsString(expenseEdit);

        mockMvc.perform(post("/editExpense")
                .header("Authorization", smart1Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        updatedUser = userService.findUserById(user.getId());
        List<Expense> actualExpense = expenseService.getExpenseHistory(String.valueOf(user.getId()));
        long idExpenseEdit = actualExpense.get(0).getId();

        double expectedBalance = 5000.0;
        double actualBalance = userService.getBalance(user.getId());

        assertEquals(30000, updatedUser.getAccountExpense(), 0);

        assertTrue(expectedBalance == actualBalance);

        assertTrue(actualExpense.size() == 1);

        assertTrue(idExpenseEdit == idExpense);

    }

}