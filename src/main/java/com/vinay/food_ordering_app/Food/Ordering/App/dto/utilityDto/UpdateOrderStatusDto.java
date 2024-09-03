package com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto;

import com.vinay.food_ordering_app.Food.Ordering.App.annotation.OrderStatusValidation;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusDto {

    @OrderStatusValidation
    private String updateOrderStatus;
}
