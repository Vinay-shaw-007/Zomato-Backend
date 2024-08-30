package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.RestaurantOwnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;

import java.util.Optional;

public interface RestaurantOwnerService {
    RestaurantOwnerEntity createRestaurantOwner(RestaurantOwnerEntity restaurantOwner);

    Optional<RestaurantOwnerEntity> getRestaurantOwnerDetails(UserEntity user);
}
