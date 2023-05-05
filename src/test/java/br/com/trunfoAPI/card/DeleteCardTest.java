package br.com.trunfoAPI.card;

import br.com.trunfoAPI.controller.CardController;
import br.com.trunfoAPI.model.entity.Card;
import br.com.trunfoAPI.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
public class DeleteCardTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CardService cardService;

    @Test
    public void deleteControllerTest() throws Exception {
        Long id = 1L;
        Card card = new Card(1L,"https://www.infoescola.com/wp-content/uploads/2017/04/leao-126767138.jpg","Leão",120.0,120.0,120.0,120);

        when(cardService.delete(id))
                .thenReturn("");

        mockMvc.perform(delete("/card/{id}", id))
                .andExpect(status().isOk());
    }
}
