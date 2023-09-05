package br.com.trunfoAPI.security.util;

import br.com.trunfoAPI.security.exception.CookieNotFoundException;
import br.com.trunfoAPI.security.model.UserSecurity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

public class CookieUtil {

    // Gerando um cookie
    public static Cookie generatedCookie(UserSecurity userSecurity) {
        String token = JWTUtil.generatedToken(userSecurity); // Gerar o token
        Cookie cookie = new Cookie("JWT", token); // Gerando o cookie com um nome e colocando o token nele
        cookie.setPath("/"); // Onde o cookie será visível
        cookie.setMaxAge(1800); // Tempo de vida do cookie
        return cookie;
    }

    // Pegar o token do cookie já existente
    public static String getToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "JWT");
        if (cookie != null) {
            return cookie.getValue();
        }
        throw new CookieNotFoundException("JWT");
    }
}
