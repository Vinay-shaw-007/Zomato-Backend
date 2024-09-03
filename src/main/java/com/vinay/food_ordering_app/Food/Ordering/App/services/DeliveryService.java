package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;

public interface DeliveryService {

    DeliveryEntity createDelivery(OrderEntity order);

    DeliveryEntity getDeliveryDetails(Long deliveryId);

    DeliveryEntity findByOrder_Id(Long orderId);

    DeliveryDto updateDeliveryStatus(DeliveryEntity delivery, DeliveryStatus deliveryStatus);

    DeliveryEntity cancelDelivery(DeliveryEntity delivery);
}
