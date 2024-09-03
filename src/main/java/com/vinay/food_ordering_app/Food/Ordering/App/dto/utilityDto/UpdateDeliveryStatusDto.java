package com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto;

import com.vinay.food_ordering_app.Food.Ordering.App.annotation.DeliveryStatusValidation;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import lombok.Data;

@Data
public class UpdateDeliveryStatusDto {

    @DeliveryStatusValidation
    private String deliveryStatus;
}
