package com.vinay.food_ordering_app.Food.Ordering.App.exceptions;

public class RuntimeConflictException extends RuntimeException{

    public RuntimeConflictException() {
    }

    public RuntimeConflictException(String message) {
        super(message);
    }
}