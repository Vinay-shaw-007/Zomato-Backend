package com.vinay.food_ordering_app.Food.Ordering.App.validation;

import com.vinay.food_ordering_app.Food.Ordering.App.annotation.DeliveryStatusValidation;
import com.vinay.food_ordering_app.Food.Ordering.App.annotation.PaymentMethodValidation;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PaymentMethodValidator implements ConstraintValidator<PaymentMethodValidation, String> {
    @Override
    public boolean isValid(String paymentMethod, ConstraintValidatorContext constraintValidatorContext) {
        if (paymentMethod.isBlank()) {
            return false;
        }

        try {
            PaymentMethod.valueOf(paymentMethod.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
