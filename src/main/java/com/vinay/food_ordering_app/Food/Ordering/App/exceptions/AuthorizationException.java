package com.vinay.food_ordering_app.Food.Ordering.App.exceptions;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
