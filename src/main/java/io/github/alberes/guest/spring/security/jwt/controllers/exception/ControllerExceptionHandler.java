package io.github.alberes.guest.spring.security.jwt.controllers.exception;

import io.github.alberes.guest.spring.security.jwt.controllers.dto.FieldErroDto;
import io.github.alberes.guest.spring.security.jwt.services.exception.AuthorizationException;
import io.github.alberes.guest.spring.security.jwt.services.exception.DuplicateRecordException;
import io.github.alberes.guest.spring.security.jwt.services.exception.ObjectNotFoundException;
import io.github.alberes.guest.spring.security.jwt.services.exception.StandardError;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<StandardError> duplicateRecordException(DuplicateRecordException duplicateRecordException,
                                                                  HttpServletRequest httpServletRequest){
        StandardError standardError = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.CONFLICT.value(),
                "Conflit",
                duplicateRecordException.getMessage(),
                httpServletRequest.getRequestURI(),
                List.of()
        );
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(
            ObjectNotFoundException objectNotFoundException, HttpServletRequest httpServletRequest){
        StandardError standardError = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Not found",
                objectNotFoundException.getMessage(),
                httpServletRequest.getRequestURI(),
                List.of()
        );
        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                         HttpServletRequest httpServletRequest){
        List<FieldError> fieldErrors = methodArgumentNotValidException.getFieldErrors();
        List<FieldErroDto> fieldErros = fieldErrors.stream().map(
                fe -> new FieldErroDto(fe.getField(), fe.getDefaultMessage())
        ).toList();

        StandardError standardError = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                methodArgumentNotValidException.getMessage(),
                httpServletRequest.getRequestURI(),
                fieldErros
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorizationException(AuthorizationException authorizationException,
                                                                HttpServletRequest httpServletRequest){
        StandardError standardError = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                authorizationException.getMessage(),
                httpServletRequest.getRequestURI(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(standardError);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<StandardError> expiredJwtException(ExpiredJwtException expiredJwtException,
                                                                HttpServletRequest httpServletRequest){
        StandardError standardError = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                expiredJwtException.getMessage(),
                httpServletRequest.getRequestURI(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standardError);
    }
}