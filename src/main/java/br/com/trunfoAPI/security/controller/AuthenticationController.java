package br.com.trunfoAPI.security.controller;


import br.com.trunfoAPI.security.model.Login;
import br.com.trunfoAPI.security.model.UserSecurity;
import br.com.trunfoAPI.security.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@AllArgsConstructor
@RequestMapping
public class AuthenticationController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login, HttpServletRequest req, HttpServletResponse res) {

        // Cria um repositório de contexto de segurança da sessão HTTP
        SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

        // Cria um token de autenticação com as credenciais fornecidas
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword()
        );

        // Autentica o token de autenticação usando o AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(token);

        if (authentication.isAuthenticated()) {

            UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
            Cookie cookie = CookieUtil.generatedCookie(userSecurity);
            res.addCookie(cookie);

//            ESSA FORMA É A ANTIGA
//            Cria um novo contexto de segurança vazio
//            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//            Define a autenticação no contexto de segurança
//            securityContext.setAuthentication(authentication);
//            Armazena o contexto de segurança atualizado no repositório da sessão
//            securityContextRepository.saveContext(securityContext, req, res);

            // Retorna o principal de autenticação (geralmente o usuário autenticado)
            return ResponseEntity.ok(authentication.getPrincipal());
        }

        // Retorna uma resposta 401 (Unauthorized) se a autenticação falhar
        return ResponseEntity.status(401).build();
    }
}
