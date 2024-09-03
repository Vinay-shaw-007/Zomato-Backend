package com.vinay.food_ordering_app.Food.Ordering.App.validation;

import com.vinay.food_ordering_app.Food.Ordering.App.annotation.DeliveryStatusValidation;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DeliveryStatusValidator implements ConstraintValidator<DeliveryStatusValidation, String> {
    @Override
    public boolean isValid(String deliveryStatus, ConstraintValidatorContext constraintValidatorContext) {
        if (deliveryStatus.isBlank()) {
            return false;
        }

        try {
            DeliveryStatus.valueOf(deliveryStatus.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
