package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusDto {
    private OrderStatus updateOrderStatus;
}
