package com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto;

import com.vinay.food_ordering_app.Food.Ordering.App.annotation.PaymentMethodValidation;
import lombok.Data;

@Data
public class UpdatePaymentMethod {

    @PaymentMethodValidation
    private String paymentMethod;
}
