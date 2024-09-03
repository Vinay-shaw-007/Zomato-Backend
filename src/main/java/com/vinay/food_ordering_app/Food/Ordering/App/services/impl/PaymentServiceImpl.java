package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.PaymentDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.PaymentEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.PaymentRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;


    @Override
    public PaymentEntity updatePaymentStatus(PaymentEntity paymentEntity, PaymentStatus paymentStatus) {
        paymentEntity.setPaymentTime(LocalDateTime.now());
        paymentEntity.setPaymentStatus(paymentStatus);
        return paymentRepository.save(paymentEntity);
    }



    @Override
    public PaymentDto getPaymentDetails(Long paymentId) {
        return null;
    }
}
