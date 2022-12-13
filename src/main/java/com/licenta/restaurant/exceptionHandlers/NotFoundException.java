package com.licenta.restaurant.exceptionHandlers;

import com.licenta.restaurant.enums.ObjectType;

public class NotFoundException extends RuntimeException{

    private static final  String NOT_FOUND = " not found.";

    public NotFoundException(String message) { super(message); }

    public NotFoundException(ObjectType objectType, String name) {

        switch (objectType) {
            case RESTAURANT -> throw new NotFoundException("Restaurant with name " + name + NOT_FOUND);
            case MENU -> throw new NotFoundException("Menu with name " + name + NOT_FOUND);
            case MENU_ITEM -> throw new NotFoundException("Menu item with name " + name + NOT_FOUND);
            case PERSON -> throw new NotFoundException("Person " + name + NOT_FOUND);
            default -> throw new NotFoundException("Not found.");
        }
    }

    public NotFoundException(ObjectType objectType, Long id) {

        switch (objectType) {
            case RESTAURANT -> throw new NotFoundException("Restaurant with name " + id + NOT_FOUND);
            case MENU -> throw new NotFoundException("Menu with name " + id + NOT_FOUND);
            case MENU_ITEM -> throw new NotFoundException("Menu item with name " + id + NOT_FOUND);
            case PERSON -> throw new NotFoundException("Person " + id + NOT_FOUND);
            default -> throw new NotFoundException("Not found.");
        }
    }
}
