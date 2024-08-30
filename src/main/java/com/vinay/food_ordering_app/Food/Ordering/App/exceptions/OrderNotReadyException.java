package com.vinay.food_ordering_app.Food.Ordering.App.exceptions;

public class OrderNotReadyException extends RuntimeException{
    public OrderNotReadyException() {
    }

    public OrderNotReadyException(String message) {
        super(message);
    }
}
