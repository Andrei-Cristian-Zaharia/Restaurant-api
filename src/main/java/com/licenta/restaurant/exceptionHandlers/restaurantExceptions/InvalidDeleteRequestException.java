package com.licenta.restaurant.exceptionHandlers.restaurantExceptions;

public class InvalidDeleteRequestException extends RuntimeException {

    public InvalidDeleteRequestException() { super("Provided account is not associated with this restaurant !"); }
}
