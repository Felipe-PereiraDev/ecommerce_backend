package br.com.felipedev.ecommerce.exception;

public class DescriptionExistsException extends RuntimeException {
    public DescriptionExistsException(String message) {
        super(message);
    }
}
