package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.RestaurantDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;

import java.util.List;

public interface RestaurantService {

    RestaurantEntity createNewRestaurant(RestaurantEntity restaurant);

    RestaurantDto getRestaurantDetailsWithMenuItems(Long restaurantId);

    RestaurantEntity getRestaurantDetails(Long restaurantId);

    RestaurantDto updateRestaurantDetails(Long restaurantId);

    RestaurantDto addMenuItemToRestaurant(RestaurantEntity restaurant, List<MenuItemDto> menuItems);

}
