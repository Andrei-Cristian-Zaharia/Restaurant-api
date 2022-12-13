package com.licenta.restaurant.exceptionHandlers.restaurantExceptions;

public class UserAlreadyHasRestaurantException extends RuntimeException{

    public UserAlreadyHasRestaurantException() { super("This user already owns a restaurant !"); }
}
