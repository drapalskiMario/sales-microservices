package br.com.microservices.productapi.modules.exception;


public class DomainApplicationException extends RuntimeException {
    public DomainApplicationException(String message) {
        super(message);
    }
}
