package com.vinay.food_ordering_app.Food.Ordering.App.validation;

import com.vinay.food_ordering_app.Food.Ordering.App.annotation.OrderStatusValidation;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class OrderStatusValidator implements ConstraintValidator<OrderStatusValidation, String> {
    @Override
    public boolean isValid(String orderStatus, ConstraintValidatorContext constraintValidatorContext) {
        if (orderStatus.isBlank()) {
            return false;
        }

        try {
            OrderStatus.valueOf(orderStatus.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
