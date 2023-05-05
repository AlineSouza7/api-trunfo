package br.com.trunfoAPI.controller;

import br.com.trunfoAPI.model.dto.CardDTO;
import br.com.trunfoAPI.model.entity.Card;
import br.com.trunfoAPI.service.CardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/card")
@CrossOrigin
public class CardController implements ImplementarController<Card, CardDTO> {

    CardService cardService;

    @Override
    @PostMapping
    public ResponseEntity<Card> create(@RequestBody @Valid CardDTO dto) {
        return ResponseEntity.ok(cardService.create(dto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Card>> listAll() {
        return ResponseEntity.ok(cardService.listAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Card> listOne(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.listOne(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Card> update(@RequestBody @Valid CardDTO dto,@PathVariable Long id) {
        return ResponseEntity.ok(cardService.update(dto,id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.delete(id));
    }
}
