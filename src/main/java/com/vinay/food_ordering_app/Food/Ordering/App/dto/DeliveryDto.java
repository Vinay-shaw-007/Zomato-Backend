package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    private Long id;

    @JsonIgnore
    private OrderDto order;

    private DeliveryPartnerDto deliveryPartner;

    private PointDto deliveryLocation;

    private Double amount;

    private DeliveryStatus deliveryStatus;

    private LocalDateTime deliveryStarted;

    private LocalDateTime deliveryCompleted;
}
