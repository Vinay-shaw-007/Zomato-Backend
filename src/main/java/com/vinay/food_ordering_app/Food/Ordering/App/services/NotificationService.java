package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;

public interface NotificationService {

    void sendNotificationToRestaurantId(RestaurantEntity restaurant, OrderEntity order);

    void notifyDeliveryPartner(DeliveryPartnerEntity deliveryPartner, DeliveryEntity delivery);
}
