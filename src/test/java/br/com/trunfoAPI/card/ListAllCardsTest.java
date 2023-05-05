package br.com.trunfoAPI.card;

import br.com.trunfoAPI.controller.CardController;
import br.com.trunfoAPI.model.entity.Card;
import br.com.trunfoAPI.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
public class ListAllCardsTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CardService cardService;

    @Test
    public void listAllControllerTest() throws Exception {
        Long id = 1L;
        Card card01 = new Card(1L,"https://www.infoescola.com/wp-content/uploads/2017/04/leao-126767138.jpg","Leão",120.0,120.0,120.0,120);
        Card card02 = new Card(2L,"https://www.infoescola.com/wp-content/uploads/2017/04/leao-126767138.jpg","Leão",120.0,120.0,120.0,120);

        List cards = List.of(card01,card02);

        when(cardService.listAll())
                .thenReturn(cards);

        mockMvc.perform(get("/card"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]").value(cards.get(0)))
                .andExpect(jsonPath("$[1]").value(cards.get(1)));
    }
}
