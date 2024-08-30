package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.NotificationDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;

import java.util.List;

public interface NotificationService {

    NotificationDto sendNotificationToCustomer(Long customerId);

    void sendNotificationToRestaurantId(RestaurantEntity restaurant, OrderEntity order);

    List<NotificationDto> getAllCustomerNotifications(Long customerId);

    void notifyDeliveryPartner(DeliveryPartnerEntity deliveryPartner, DeliveryEntity delivery);
}
