package com.licenta.restaurant.exceptionHandlers.restaurantExceptions;

public class InvalidUserAccount extends RuntimeException {

    public InvalidUserAccount() { super("Invalid account, email or password might be wrong !"); }
}
