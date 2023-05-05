package br.com.trunfoAPI.user;

import br.com.trunfoAPI.controller.UserController;
import br.com.trunfoAPI.model.dto.UserDTO;
import br.com.trunfoAPI.model.entity.User;
import br.com.trunfoAPI.model.enums.TypeUser;
import br.com.trunfoAPI.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class PostUserTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void createControllerTest() throws Exception {
        User user = new User(1L, TypeUser.JOGADOR, "player","https://tudocommoda.com/wp-content/uploads/2022/01/pessoa-interessante.png","123");
        UserDTO userDTO = new UserDTO(TypeUser.JOGADOR, "player","https://tudocommoda.com/wp-content/uploads/2022/01/pessoa-interessante.png","123");

        when(userService.create(userDTO))
                .thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(user));
    }
}
