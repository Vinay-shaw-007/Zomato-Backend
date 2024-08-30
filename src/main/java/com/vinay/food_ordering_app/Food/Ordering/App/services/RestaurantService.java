package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.RestaurantDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;

import java.util.List;

public interface RestaurantService {

    RestaurantEntity createNewRestaurant(RestaurantEntity restaurant);

    RestaurantDto getRestaurantDetails(Long restaurantId);

    RestaurantDto getAllRestaurants();

    RestaurantDto updateRestaurantDetails(Long restaurantId);

    RestaurantDto getRestaurantAllMenuItems(Long restaurantId);

    RestaurantDto addMenuItemToRestaurant(Long restaurantId, List<MenuItemDto> menuItems);

}
