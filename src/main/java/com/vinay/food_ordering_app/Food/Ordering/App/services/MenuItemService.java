package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.MenuItemEntity;

import java.util.List;

public interface MenuItemService {

    MenuItemEntity createNewMenuItem(MenuItemEntity menuItem);

    List<MenuItemEntity> getAllMenuItems(Long restaurantId);
}
