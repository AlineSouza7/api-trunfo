package br.com.trunfoAPI.security.exception;

public class CookieNotFoundException extends RuntimeException {

    public CookieNotFoundException(String name) {
        super("O cookie " + name + " não foi encontrado!");

    }

}
