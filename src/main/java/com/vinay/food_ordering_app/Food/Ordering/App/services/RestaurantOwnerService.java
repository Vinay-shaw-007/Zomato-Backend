package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.RestaurantDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.RestaurantOwnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;

import java.util.List;

public interface RestaurantOwnerService {
    RestaurantOwnerEntity createRestaurantOwner(RestaurantOwnerEntity restaurantOwner);

    RestaurantOwnerEntity getRestaurantOwnerDetails(UserEntity user);

    RestaurantOwnerEntity getCurrentRestaurantOwner();

    OrderDto updateOrderStatus(Long orderId, OrderStatus orderStatus);

    RestaurantDto addMenuItemToRestaurant(Long restaurantId, List<MenuItemDto> menuItemDto);
}
