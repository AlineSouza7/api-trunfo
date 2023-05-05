package br.com.trunfoAPI.card;

import br.com.trunfoAPI.controller.CardController;
import br.com.trunfoAPI.model.dto.CardDTO;
import br.com.trunfoAPI.model.entity.Card;
import br.com.trunfoAPI.service.CardService;
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
import static org.hamcrest.Matchers.equalTo;

@WebMvcTest(CardController.class)
public class PostCardTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CardService cardService;

    @Test
    public void createControllerTest() throws Exception {
        Card card = new Card(1L, "https://www.infoescola.com/wp-content/uploads/2017/04/leao-126767138.jpg", "Leão", 120.0, 120.0, 120.0, 120);
        CardDTO cardDTO = new CardDTO("https://www.infoescola.com/wp-content/uploads/2017/04/leao-126767138.jpg", "Leão", 120.0, 120.0, 120.0, 120);

        when(cardService.create(cardDTO))
                .thenReturn(card);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(cardDTO);

        mockMvc.perform(post("/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(card));
    }

}
