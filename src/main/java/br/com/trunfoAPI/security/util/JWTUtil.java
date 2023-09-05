package br.com.trunfoAPI.security.util;

import br.com.trunfoAPI.security.respository.UserRepositorySecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    private static UserRepositorySecurity userRepositorySecurity;

    @Autowired
    public JWTUtil(UserRepositorySecurity userRepositorySecurity) {
        JWTUtil.userRepositorySecurity = userRepositorySecurity;
    }

    

}
