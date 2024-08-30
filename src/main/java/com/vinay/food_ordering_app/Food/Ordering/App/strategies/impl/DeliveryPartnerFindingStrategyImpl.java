package com.vinay.food_ordering_app.Food.Ordering.App.strategies.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.DeliveryPartnerRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.strategies.DeliveryPartnerFindingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerFindingStrategyImpl implements DeliveryPartnerFindingStrategy {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    @Override
    public DeliveryPartnerEntity findNearestDeliveryPartner(RestaurantEntity restaurant) {
        return deliveryPartnerRepository.findNearestDeliveryPartner(restaurant.getLocation());
    }
}
