package com.vinay.food_ordering_app.Food.Ordering.App.exceptions;

public class NoAvailableDeliveryPartnerException extends RuntimeException{

    public NoAvailableDeliveryPartnerException() {
    }

    public NoAvailableDeliveryPartnerException(String message) {
        super(message);
    }
}
