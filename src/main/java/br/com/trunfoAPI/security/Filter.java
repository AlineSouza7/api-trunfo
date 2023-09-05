package br.com.trunfoAPI.security;

import br.com.trunfoAPI.security.exception.CookieNotFoundException;
import br.com.trunfoAPI.security.model.UserSecurity;
import br.com.trunfoAPI.security.util.CookieUtil;
import br.com.trunfoAPI.security.util.JWTUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class Filter extends OncePerRequestFilter { // Permite que cada vez que houver uma requisição o filtro seja aplicado

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (privateRoute(request.getRequestURI())) {
            try {
                String token = CookieUtil.getToken(request);
                UserSecurity userSecurity = JWTUtil.getUserSecurity(token);
                response.addCookie(CookieUtil.generatedCookie(userSecurity)); // Reseta o cookie para que o usuário não precisa fazer login novamente

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userSecurity.getUsername(), null, userSecurity.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JWTDecodeException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (CookieNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    private Boolean privateRoute(String url) {
        return url.contains("/card") || url.contains("/user");
    }
}
