package br.com.trunfoAPI.user;

import br.com.trunfoAPI.controller.UserController;
import br.com.trunfoAPI.model.entity.User;
import br.com.trunfoAPI.model.enums.TypeUser;
import br.com.trunfoAPI.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class ListOneUserTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void listOneControllerTest() throws Exception {
        Long id = 1L;
        User user = new User(1L, TypeUser.JOGADOR, "player","https://tudocommoda.com/wp-content/uploads/2022/01/pessoa-interessante.png","123");

        when(userService.listOne(id))
                .thenReturn(user);

        mockMvc.perform(get("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(user));
    }
}
