package com.dancea.schemaflex.errors;

public class JsonFileNotFoundException extends RuntimeException {
    public JsonFileNotFoundException(String message) {
        super(message);
    }

}
