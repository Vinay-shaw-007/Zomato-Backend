package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.RestaurantDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.MenuItemEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.RestaurantRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.MenuItemService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.OrderService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemService menuItemService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Override
    public RestaurantEntity createNewRestaurant(RestaurantEntity restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantDto getRestaurantDetails(Long restaurantId) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: "+restaurantId));

        List<MenuItemDto> list = menuItemService
                .getAllMenuItems(restaurantId)
                .stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class))
                .toList();

        RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);

        restaurantDto.setMenuItems(list);

        return restaurantDto;
    }

    @Override
    public RestaurantDto getAllRestaurants() {
        return null;
    }

    @Override
    public RestaurantDto updateRestaurantDetails(Long restaurantId) {
        return null;
    }

    @Override
    public RestaurantDto getRestaurantAllMenuItems(Long restaurantId) {
        return null;
    }

    @Override
    public RestaurantDto addMenuItemToRestaurant(Long restaurantId, List<MenuItemDto> menuItems) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: "+restaurantId));

        List<MenuItemDto> savedMenuItems = menuItems.stream()
                .map(menuItem -> {
                    MenuItemEntity item = modelMapper.map(menuItem, MenuItemEntity.class);
                    item.setRestaurant(restaurant);
                    return menuItemService.createNewMenuItem(item);
                })
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class))
                .toList();
        RestaurantDto dto = modelMapper.map(restaurant, RestaurantDto.class);
        dto.setMenuItems(savedMenuItems);
        return dto;
    }
}
