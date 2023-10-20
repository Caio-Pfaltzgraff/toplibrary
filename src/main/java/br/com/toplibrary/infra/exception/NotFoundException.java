package br.com.toplibrary.infra.exception;

public class NotFoundException extends BusinessException{
    public NotFoundException() {
        super("Resource not found.");
    }
}
