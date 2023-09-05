package br.com.trunfoAPI.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum TypeUser implements GrantedAuthority {

    JOGADOR,ADMINISTRADOR;

    @Override
    public String getAuthority() {
        return this.name();
    }

}
