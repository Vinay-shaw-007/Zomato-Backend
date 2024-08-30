package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;

public interface DeliveryService {

    DeliveryEntity createDelivery(Long orderId);

    DeliveryEntity getDeliveryDetails(Long deliveryId);

    DeliveryDto updateDeliveryStatus(Long orderId, DeliveryStatus deliveryStatus);

}
