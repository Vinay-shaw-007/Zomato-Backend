package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.MenuItemEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.MenuItemRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Override
    public MenuItemEntity createNewMenuItem(MenuItemEntity menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public List<MenuItemEntity> getAllMenuItems(Long restaurantId) {
        return menuItemRepository.findByRestaurant_Id(restaurantId);
    }
}
