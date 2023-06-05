package br.com.trunfoAPI.service;

import br.com.trunfoAPI.model.dto.UserDTO;
import br.com.trunfoAPI.model.entity.User;
import br.com.trunfoAPI.model.enums.TypeUser;
import br.com.trunfoAPI.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements ImplementarService<User, UserDTO> {

    UserRepository userRepository;

    @Override
    public User create(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        return userRepository.save(user);
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User listOne(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException("Não encontrado!!");
    }

    @Override
    public User update(UserDTO dto,Long id) {
        User user = listOne(id);
        BeanUtils.copyProperties(dto,user);
        return userRepository.save(user);
    }

    @Override
    public String delete(Long id) {
        User user = listOne(id);
        userRepository.delete(user);
        return "Usuário deletado com sucesso!";
    }


    public TypeUser[] typesUser() {
        return TypeUser.values();
    }

    public User login(String user, String password) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(user, password);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException("Login inválido!!");
    }
}
