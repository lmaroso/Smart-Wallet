package integationTests;

import app.SmartWalletApplication;
import app.dto.IncomeDTO;
import app.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDateTime;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SmartWalletApplication.class)
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void successRegister() throws Exception {

        UserDTO user = new UserDTO("Fede", "fedejmartinez@gmail.com", "f");
        String jsonRequest = mapper.writeValueAsString(user);

        MvcResult result = mockMvc
                .perform(post("/register").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void registerWithInvalidMail() throws Exception {

        UserDTO user = new UserDTO("Jose", "jose.com", "f");
        String jsonRequest = mapper.writeValueAsString(user);

        MvcResult result = mockMvc
                .perform(post("/register").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

    }

    /*@Test
    public void testSuccessLogin() throws Exception {

        UserDTO registerUser = new UserDTO("Fede", "fedejmartinez@gmail.com", "f");
        LoginDTO loginUser = new LoginDTO();
        loginUser.setUsername("fedejmartinez@gmail.com");
        loginUser.setPassword("f");
        String registerJsonRequest = mapper.writeValueAsString(registerUser);
        String loginJsonRequest = mapper.writeValueAsString(loginUser);

         mockMvc.perform(post("/register").content(registerJsonRequest).contentType(MediaType.APPLICATION_JSON));

        //assertEquals(200, result.getResponse().getErrorMessage());

    }*/

    @Test
    public void testAddIncome() throws Exception{

        //UserDTO user = new UserDTO("Ama", "amahjaime@gmail.com", "ama");
        IncomeDTO income = new IncomeDTO("1", "Sueldo", "Sueldo mensual", 35000, LocalDateTime.now(), false);
        String jsonRequest = mapper.writeValueAsString(income);

       String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiQW1hIiwiZW1haWwiOiJkYXZpbGFtZWxpZTIxQGdtYWlsLmNvbSIsInBhc3N3b3JkIjoiYW1hbGlhamFpbWUifQ.hb7l6gYsKoWQsHbybEyys41dU231Y_zPZhy1troRgY4";

        MvcResult result = mockMvc
                .perform(post("/addIncome").
                        header("Authorization", token)
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }
}
