package com.licenta.restaurant;

import com.licenta.restaurant.exceptionHandlers.AlreadyExistsException;
import com.licenta.restaurant.exceptionHandlers.NotFoundException;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.InvalidDeleteRequestException;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.InvalidUserAccount;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.UserAlreadyHasRestaurantException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AlreadyExistsException.class, UserAlreadyHasRestaurantException.class})
    public ResponseEntity<Object> handleAlreadyExist(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidUserAccount.class, InvalidDeleteRequestException.class})
    public ResponseEntity<Object> handleInvalidAuth(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
