package br.com.microservices.productapi.config.execption;

import br.com.microservices.productapi.modules.exception.AuthenticationException;
import br.com.microservices.productapi.modules.exception.DomainApplicationException;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomException {

    @Getter
    private List<String> errors;

    public CustomException(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult
                .getAllErrors()
                .forEach(error -> this.errors.add(error.getDefaultMessage()));
    }

    public CustomException(DomainApplicationException exception) {
        this.errors = Arrays.asList(exception.getMessage());
    }

    public CustomException(ResponseStatusException exception) {
        this.errors = Arrays.asList(exception.getMessage());
    }

    public CustomException(MethodArgumentTypeMismatchException exception) {
        this.errors = Arrays.asList(exception.getLocalizedMessage());
    }

    public CustomException(AuthenticationException exception) {
        this.errors = Arrays.asList(exception.getLocalizedMessage());
    }
}
