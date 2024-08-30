package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import lombok.Data;

@Data
public class UpdateDeliveryStatusDto {

    private DeliveryStatus deliveryStatus;
}
