package io.github.alberes.guest.spring.security.jwt.services.exception;

public class DuplicateRecordException extends RuntimeException{

    public DuplicateRecordException(String msg) {
        super(msg);
    }

    public DuplicateRecordException(String msg, Throwable cause) {
        super(msg, cause);
    }
}