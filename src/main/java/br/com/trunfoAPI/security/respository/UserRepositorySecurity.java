package br.com.trunfoAPI.security.respository;

import br.com.trunfoAPI.security.model.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositorySecurity extends JpaRepository<UserSecurity, Long> {

    UserSecurity findByUser_Username(String user);
}
