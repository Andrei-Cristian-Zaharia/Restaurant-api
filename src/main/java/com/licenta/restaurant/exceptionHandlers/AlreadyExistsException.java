package com.licenta.restaurant.exceptionHandlers;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException() { super("This object already exists, try creating a new one !"); }

    public AlreadyExistsException(String entity) {
        super("This " + entity + " already exists, try creating a new one !");
    }
}
