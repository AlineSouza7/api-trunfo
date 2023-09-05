package br.com.trunfoAPI.security;

import br.com.trunfoAPI.model.enums.TypeUser;
import br.com.trunfoAPI.security.service.JpaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
@AllArgsConstructor
public class Configuration {

    private final JpaService jpaService;

    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // Configura o serviço de detalhes do usuário e um codificador de senha
        authenticationManagerBuilder.userDetailsService(jpaService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Obtém o gerenciador de autenticação da configuração
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // Configura permissões para as rotas
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/login", "/user").permitAll()
                .requestMatchers(HttpMethod.POST, "/card").hasAuthority(TypeUser.ADMINISTRADOR.getAuthority())
                .requestMatchers(HttpMethod.PUT, "/card").hasAuthority(TypeUser.ADMINISTRADOR.getAuthority())
                .anyRequest().authenticated());

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(new Filter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(httpSecurityCorsConfigurer -> corsConfigurationSource());
        return httpSecurity.build();
    }

    //Vai permitir comunicação do back com o front
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); //trocar a porta, caso precise
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
