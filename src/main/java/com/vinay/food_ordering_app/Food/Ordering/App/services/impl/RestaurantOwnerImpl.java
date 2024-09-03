package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.RestaurantDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.RestaurantOwnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.AuthorizationException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.RestaurantOwnerRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.OrderService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantOwnerService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantOwnerImpl implements RestaurantOwnerService {

    private final RestaurantOwnerRepository restaurantOwnerRepository;
    private final OrderService orderService;
    private final RestaurantService restaurantService;

    @Override
    public RestaurantOwnerEntity createRestaurantOwner(RestaurantOwnerEntity restaurantOwner) {
        return restaurantOwnerRepository.save(restaurantOwner);
    }

    @Override
    public RestaurantOwnerEntity getRestaurantOwnerDetails(UserEntity user) {
        return restaurantOwnerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("User don't have restaurant owner role id: "+user.getId()));
    }


    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        RestaurantOwnerEntity restaurantOwner = getCurrentRestaurantOwner();

        OrderEntity order = orderService.getOrderDetails(orderId);

        if (!order.getRestaurant().getRestaurantOwner().equals(restaurantOwner)) {
            throw new AuthorizationException("You are not authorized to update the status of this order.");
        }

        return orderService.updateOrderStatus(order, orderStatus);
    }

    @Override
    public RestaurantDto addMenuItemToRestaurant(Long restaurantId, List<MenuItemDto> menuItemDto) {
        RestaurantOwnerEntity restaurantOwner = getCurrentRestaurantOwner();

        RestaurantEntity restaurant = restaurantService.getRestaurantDetails(restaurantId);

        if (!restaurant.getRestaurantOwner().equals(restaurantOwner)) {
            throw new AuthorizationException("You are not authorized to add menu items to this restaurant.");
        }

        return restaurantService.addMenuItemToRestaurant(restaurant, menuItemDto);
    }

    @Override
    public RestaurantOwnerEntity getCurrentRestaurantOwner() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return restaurantOwnerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "RestaurantOwner not associated with user with ID: "+user.getId()
                ));
    }
}
