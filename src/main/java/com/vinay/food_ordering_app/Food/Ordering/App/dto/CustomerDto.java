package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private Long id;

    private UserDto user;

    private PaymentMethod paymentMethod;

    private List<OrderDto> orders;
}
