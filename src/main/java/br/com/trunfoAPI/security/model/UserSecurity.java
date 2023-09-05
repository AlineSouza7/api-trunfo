package br.com.trunfoAPI.security.model;

import br.com.trunfoAPI.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSecurity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user; // Referência para um objeto User, associado a este usuário

    private Collection<GrantedAuthority> authorities; // As autorizações (papéis) deste usuário

    private boolean accountNonExpired; // Indica se a conta do usuário não está expirada
    private boolean accountNonLocked; // Indica se a conta do usuário não está bloqueada
    private boolean credentialsNonExpired; // Indica se as credenciais do usuário não estão expiradas
    private boolean enabled; // Indica se o usuário está habilitado

    @Override
    public String getPassword() {
        return user.getPassword(); // Retorna a senha do usuário
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // Retorna o nome de usuário do usuário
    }
}
