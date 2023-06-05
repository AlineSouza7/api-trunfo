package br.com.trunfoAPI.controller;

import br.com.trunfoAPI.model.dto.UserDTO;
import br.com.trunfoAPI.model.entity.User;
import br.com.trunfoAPI.model.enums.TypeUser;
import br.com.trunfoAPI.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController implements ImplementarController<User, UserDTO> {

    UserService userService;

    @Override
    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserDTO dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(userService.listAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> listOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.listOne(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody @Valid UserDTO dto,@PathVariable Long id) {
        return ResponseEntity.ok(userService.update(dto,id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/types")
    public ResponseEntity<TypeUser[]> typesUser() {
        return ResponseEntity.ok(userService.typesUser());
    }

    @GetMapping("/login/{user}/{password}")
    public ResponseEntity<User> login(@PathVariable String user, @PathVariable String password) {
        return ResponseEntity.ok(userService.login(user, password));
    }
}
