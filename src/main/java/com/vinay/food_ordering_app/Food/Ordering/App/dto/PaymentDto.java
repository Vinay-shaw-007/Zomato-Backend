package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentMethod;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;

    @JsonIgnore
    private OrderDto order;

    private Double amount;

    @JsonIgnore
    private WalletDto wallet;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentTime;
}
