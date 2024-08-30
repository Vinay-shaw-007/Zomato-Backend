package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.*;

public interface AuthService {

    UserDto signUp(SignUpDto signUpDto);

    String login(String email, String password);

    DeliveryPartnerDto onBoardDeliveryPartner(Long userId, OnBoardDeliveryPartnerDto onBoardDeliveryPartner);

    RestaurantOwnerDto onBoardRestaurantOwner(Long userId, String registrationNumber);

    RestaurantDto onBoardRestaurant(Long ownerId, OnBoardRestaurantDto restaurantDetails);
}
