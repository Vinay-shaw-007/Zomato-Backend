package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnBoardDeliveryPartnerDto {
    private String vehicleId;

    private PointDto deliveryPartnerLocation;
}
