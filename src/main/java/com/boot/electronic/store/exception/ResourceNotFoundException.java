package com.boot.electronic.store.exception;

public class ResourceNotFoundException  extends  RuntimeException{

    public ResourceNotFoundException() {
        super("Resource not found !!!");
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
