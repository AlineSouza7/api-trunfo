package br.com.trunfoAPI.service;

import br.com.trunfoAPI.model.dto.UserDTO;
import br.com.trunfoAPI.model.entity.User;
import br.com.trunfoAPI.model.enums.TypeUser;
import br.com.trunfoAPI.repository.UserRepository;
import br.com.trunfoAPI.security.model.UserSecurity;
import br.com.trunfoAPI.security.respository.UserRepositorySecurity;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements ImplementarService<User, UserDTO> {

    private UserRepository userRepository;
    private UserRepositorySecurity userRepositorySecurity;

    @Override
    public User create(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        user.setPassword(encoder.encode(user.getPassword()));

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUser(user);
        userSecurity.setEnabled(true);
        userSecurity.setAccountNonLocked(true);
        userSecurity.setAccountNonExpired(true);
        userSecurity.setCredentialsNonExpired(true);
        userSecurity.setAuthorities(new ArrayList<>());

        return userRepositorySecurity.save(userSecurity).getUser();
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
    public User update(UserDTO dto, Long id) {
        User user = listOne(id);
        BeanUtils.copyProperties(dto, user);
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

    public void save(User user) {
        userRepository.save(user);
    }
}
