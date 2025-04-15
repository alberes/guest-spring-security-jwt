package io.github.alberes.guest.spring.security.jwt.services.exception;

public class AuthorizationException extends RuntimeException{

    public AuthorizationException(String msg) {
        super(msg);
    }

    public AuthorizationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
