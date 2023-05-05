package br.com.trunfoAPI.service;

import br.com.trunfoAPI.model.dto.CardDTO;
import br.com.trunfoAPI.model.entity.Card;
import br.com.trunfoAPI.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService implements ImplementarService<Card, CardDTO> {

    CardRepository cardRepository;

    @Override
    public Card create(CardDTO dto) {
        Card card = new Card();
        BeanUtils.copyProperties(dto,card);
        return cardRepository.save(card);
    }

    @Override
    public List<Card> listAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card listOne(Long id) {
        Optional<Card> cardOptional = cardRepository.findById(id);
        if (cardOptional.isPresent()) {
            return cardOptional.get();
        }
        throw new RuntimeException("Não encontrado!");
    }

    @Override
    public Card update(CardDTO dto, Long id) {
        Card card = listOne(id);
        BeanUtils.copyProperties(dto,card);
        return cardRepository.save(card);
    }

    @Override
    public String delete(Long id) {
        Card card = listOne(id);
        cardRepository.delete(card);
        return "Carta deletada com sucesso!";
    }
}
