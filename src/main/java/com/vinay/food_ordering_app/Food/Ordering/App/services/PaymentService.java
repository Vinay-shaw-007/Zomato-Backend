package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.PaymentDto;

public interface PaymentService {

    PaymentDto createPayment(OrderDto order);

    PaymentDto getPaymentDetails(Long paymentId);
}
