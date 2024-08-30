package com.vinay.food_ordering_app.Food.Ordering.App.strategies;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;

public interface DeliveryPartnerFindingStrategy {

    DeliveryPartnerEntity findNearestDeliveryPartner(RestaurantEntity restaurant);
}
