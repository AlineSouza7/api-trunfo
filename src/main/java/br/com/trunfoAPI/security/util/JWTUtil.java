package br.com.trunfoAPI.security.util;

import br.com.trunfoAPI.security.model.UserSecurity;
import br.com.trunfoAPI.security.respository.UserRepositorySecurity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    // Chamada da repository que possui o método para identificar o user
    private static UserRepositorySecurity userRepositorySecurity;

    // Senha para decriptação da senha do JWT
    @Value("${senhaJWT}")
    private static String password;

    // Só é necessário pois o userRepository é somente uma declaração da classe e não um objeto
    @Autowired
    public JWTUtil(UserRepositorySecurity userRepositorySecurity) {
        JWTUtil.userRepositorySecurity = userRepositorySecurity;
    }

    // Criando um token
    public static String generatedToken(UserSecurity userSecurity) {
        Algorithm algorithm = Algorithm.HMAC256(password);
        return JWT.create()
                .withIssuer("ALINE") // Emissor
                .withSubject(userSecurity.getUsername()) // Algum atributo do objeto que será seu identificador
                .withIssuedAt(new Date()) // Data de emissão
                .withExpiresAt(new Date(new Date().getTime() + 18000)) // Tempo de expiração do token
                .sign(algorithm); // Senha para decriptar
    }

    // Vai retornar o usuário pelo subject
    public static UserSecurity getUserSecurity(String token) {
        String username =  JWT.decode(token).getSubject();
        return userRepositorySecurity.findByUser_Username(username);
    }


}
