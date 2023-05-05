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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class ListAllUsersTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void listAllControllerTest() throws Exception {
        User user01 = new User(1L, TypeUser.JOGADOR, "player","https://tudocommoda.com/wp-content/uploads/2022/01/pessoa-interessante.png","123");
        User user02 = new User(2L, TypeUser.JOGADOR, "player","https://tudocommoda.com/wp-content/uploads/2022/01/pessoa-interessante.png","123");

        List users = List.of(user01,user02);

        when(userService.listAll())
                .thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(users.get(0)))
                .andExpect(jsonPath("$[1]").value(users.get(1)));
    }
}
