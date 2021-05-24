package integationTests;

import app.SmartWalletApplication;
import app.api.user.UserService;
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

    private String smart2Token;

    @Before
    public void setup() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                 .apply(springSecurity())
                                 .build();

        //Registra usuarios para tests.
        UserDTO smart1RegisterUser  = new UserDTO("Smart1", "smart.wallet.app1@gmail.com", "sw");
        UserDTO smart2RegisterUser  = new UserDTO("Smart1", "smart.wallet.app2@gmail.com", "sw");

        String smart1RegisterJsonRequest  = mapper.writeValueAsString(smart1RegisterUser);
        String smart2RegisterJsonRequest  = mapper.writeValueAsString(smart2RegisterUser);

        mockMvc.perform(post("/register").content(smart1RegisterJsonRequest)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andReturn();

        mockMvc.perform(post("/register").content(smart2RegisterJsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Habilita usuarios.
        userService.enableUser("smart.wallet.app1@gmail.com");
        userService.enableUser("smart.wallet.app2@gmail.com");

        //Loguea usuarios para utilizar en tests.
        LoginDTO smart2LoginUser = new LoginDTO();
        smart2LoginUser.setUsername("smart.wallet.app2@gmail.com");
        smart2LoginUser.setPassword("sw");

        String smart2LoginJsonRequest = mapper.writeValueAsString(smart2LoginUser);

        //Realiza logins y obtiene tokens.
        smart2Token = mockMvc.perform(post("/login").content(smart2LoginJsonRequest)
                             .contentType(MediaType.APPLICATION_JSON))
                             .andExpect(status().isOk())
                             .andReturn()
                             .getResponse()
                             .getHeader("Authorization");

    }

    @Test
    public void successRegister() throws Exception {

        UserDTO user = new UserDTO("Fede", "fedejmartinez@smartwallet.com", "f");
        String jsonRequest = mapper.writeValueAsString(user);

        mockMvc.perform(post("/register").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void registerWithInvalidMail() throws Exception {

        UserDTO user = new UserDTO("Jose", "jose.com", "f");
        String jsonRequest = mapper.writeValueAsString(user);


        mockMvc.perform(post("/register").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    public void testSuccessLogin() throws Exception {

        LoginDTO loginUser = new LoginDTO();
        loginUser.setUsername("smart.wallet.app1@gmail.com");
        loginUser.setPassword("sw");

        String loginJsonRequest = mapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/login").content(loginJsonRequest).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testIncorrectPasswordLogin() throws Exception {

        LoginDTO loginUser = new LoginDTO();
        loginUser.setUsername("smart.wallet.app1@gmail.com");
        loginUser.setPassword("incorrect_password");

        String loginJsonRequest = mapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/login").content(loginJsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()).andReturn();

    }

    @Test
    public void testIncorrectUserLogin() throws Exception {

        LoginDTO loginUser = new LoginDTO();
        loginUser.setUsername("incorrect_email@gmail.com");
        loginUser.setPassword("sw");

        String loginJsonRequest = mapper.writeValueAsString(loginUser);

        MvcResult result = mockMvc.perform(post("/login").content(loginJsonRequest).contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().isUnauthorized())
                .andReturn();

        assertEquals(401, result.getResponse().getStatus());

    }

    @Test
    public void testAddIncome() throws Exception{

        User user = userService.findUserByEmail("smart.wallet.app2@gmail.com");
        IncomeDTO income = new IncomeDTO(user.getId(), "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);
        String jsonRequest = mapper.writeValueAsString(income);

        mockMvc.perform(post("/addIncome")
                .header("Authorization", smart2Token)
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

    }
}
