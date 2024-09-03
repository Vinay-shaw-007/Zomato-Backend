package com.vinay.food_ordering_app.Food.Ordering.App.annotation;

import com.vinay.food_ordering_app.Food.Ordering.App.validation.DeliveryStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {DeliveryStatusValidator.class})
public @interface DeliveryStatusValidation {

    String message() default "Invalid delivery status provided.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
