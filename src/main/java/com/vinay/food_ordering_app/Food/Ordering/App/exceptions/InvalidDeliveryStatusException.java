package com.vinay.food_ordering_app.Food.Ordering.App.exceptions;

public class InvalidDeliveryStatusException extends RuntimeException{
    public InvalidDeliveryStatusException() {
    }

    public InvalidDeliveryStatusException(String message) {
        super(message);
    }
}
