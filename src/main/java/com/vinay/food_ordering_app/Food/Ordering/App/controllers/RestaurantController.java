package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.RestaurantDto;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/restaurant")
@Tag(name = "Restaurant API", description = "Endpoints for managing restaurant details")
@Secured(value = "ROLE_ADMIN")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Get Restaurant Details", description = "Retrieve detailed information about a restaurant, including its menu items")
    @GetMapping(path = "/{restaurantId}")
    public ResponseEntity<RestaurantDto> getRestaurantDetails(
                                                                @Parameter(description = "ID of the restaurant to retrieve details for", required = true)
                                                                @PathVariable Long restaurantId) {
        return new ResponseEntity<>(restaurantService.getRestaurantDetailsWithMenuItems(restaurantId), HttpStatus.CREATED);
    }
}
