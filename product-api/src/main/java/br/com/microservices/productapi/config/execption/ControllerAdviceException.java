package br.com.microservices.productapi.config.execption;

import br.com.microservices.productapi.modules.exception.AuthenticationException;
import br.com.microservices.productapi.modules.exception.DomainApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomException handleException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return new CustomException(bindingResult);
    }

    @ExceptionHandler(DomainApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomException handleDomainException(DomainApplicationException exception) {
        return new CustomException(exception);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomException handleResponseException(ResponseStatusException exception) {
        return new CustomException(exception);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomException handleInvalidArgumentException(MethodArgumentTypeMismatchException exception) {
        return new CustomException(exception);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomException handleAuthenticationException(AuthenticationException exception) {
        return new CustomException(exception);
    }
}
