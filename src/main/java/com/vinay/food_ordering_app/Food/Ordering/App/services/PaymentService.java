package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.PaymentDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.PaymentEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentStatus;

public interface PaymentService {

    PaymentEntity updatePaymentStatus(PaymentEntity paymentEntity, PaymentStatus paymentStatus);

    PaymentDto getPaymentDetails(Long paymentId);
}
