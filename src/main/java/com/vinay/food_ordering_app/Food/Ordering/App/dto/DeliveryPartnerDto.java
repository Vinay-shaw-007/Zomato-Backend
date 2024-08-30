package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerDto {

    private Long id;

    private UserDto user;

    private String vehicleId;

    private Boolean availabilityStatus;

    private PointDto currentLocation;
}
