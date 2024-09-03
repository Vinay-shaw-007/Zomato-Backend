package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.UserDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;

public interface UserService {

    UserEntity getUserDetails(Long userId);

    UserEntity findByEmail(String email);
}
