
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hainguyen.security.controller.UserController;
import com.hainguyen.security.dto.request.UserRequest;
import com.hainguyen.security.model.User;
import com.hainguyen.security.repository.UserRepository;
import com.hainguyen.security.service.UserService;


@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private UserRepository repo;

    @Test
    public void testCreateUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@gmail.com");
        userRequest.setPassword("test");
        userRequest.setFullName("test");
        userRequest.setGender("Male");
        userRequest.setBirthday(new Date());
        userRequest.setCity("Ho Chi Minh");
        userRequest.setPhone("0909123123");
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        Mockito.when(service.save(user)).thenReturn(user);
        String url = "api/user/save";
        mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .content(new ObjectMapper().writeValueAsString(userRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
