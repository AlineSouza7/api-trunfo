package br.com.trunfoAPI.security.service;

import br.com.trunfoAPI.security.respository.UserRepositorySecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaService implements UserDetailsService {

    @Autowired
    private UserRepositorySecurity userRepositorySecurity;

    // Serve para buscar o usuário pelo ser username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositorySecurity.findByUser_Username(username);
    }
}
