package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.RestaurantOwnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.RestaurantOwnerRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantOwnerImpl implements RestaurantOwnerService {

    private final RestaurantOwnerRepository restaurantOwnerRepository;

    @Override
    public RestaurantOwnerEntity createRestaurantOwner(RestaurantOwnerEntity restaurantOwner) {
        return restaurantOwnerRepository.save(restaurantOwner);
    }

    @Override
    public Optional<RestaurantOwnerEntity> getRestaurantOwnerDetails(UserEntity user) {
        return restaurantOwnerRepository.findByUser(user);
    }
}
