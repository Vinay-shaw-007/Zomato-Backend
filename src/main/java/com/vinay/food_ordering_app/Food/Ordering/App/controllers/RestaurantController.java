package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.RestaurantDto;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping(path = "/{restaurantId}")
    public ResponseEntity<RestaurantDto> addMenuItemToRestaurant(@PathVariable Long restaurantId,
                                                                 @RequestBody List<MenuItemDto> menuItemDto) {
        return new ResponseEntity<>(restaurantService.addMenuItemToRestaurant(restaurantId, menuItemDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{restaurantId}")
    public ResponseEntity<RestaurantDto> getRestaurantDetails(@PathVariable Long restaurantId) {
        return new ResponseEntity<>(restaurantService.getRestaurantDetails(restaurantId), HttpStatus.CREATED);
    }
}
