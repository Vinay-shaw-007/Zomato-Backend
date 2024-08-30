package com.vinay.food_ordering_app.Food.Ordering.App.repositories;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.MenuItemEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByRestaurant(RestaurantEntity restaurant);
    List<MenuItemEntity> findByRestaurant_Id(Long restaurantId);
}
