package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.RestaurantDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.UpdateOrderStatusDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Secured(value = "ROLE_RESTAURANT_OWNER")
@Tag(name = "Restaurant Owner API", description = "Endpoints for managing order status and adding new menu items by restaurant owner.")
@RequestMapping(path = "/restaurant-owner")
public class RestaurantOwnerController {

    private final RestaurantOwnerService restaurantOwnerService;

    @Operation(summary = "Add Menu Items To Restaurant", description = "Add new menu items to a specific restaurant.")
    @PostMapping(path = "/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantDto> addMenuItemToRestaurant(
                                                                    @Parameter(description = "ID of the restaurant to add new menu items", required = true)
                                                                    @PathVariable Long restaurantId,
                                                                    @RequestBody List<MenuItemDto> menuItemDto) {
        return new ResponseEntity<>(restaurantOwnerService.addMenuItemToRestaurant(restaurantId, menuItemDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Order Status", description = "Update the order status by restaurant owner like preparing, or ready_for_pickup.")
    @PostMapping(path = "/order/{orderId}")
    public ResponseEntity<OrderDto> updateOrderStatus(
                                                        @Parameter(description = "ID of the order to update", required = true)
                                                        @PathVariable Long orderId,
                                                        @Parameter(description = "DTO containing the new order status", required = true)
                                                        @RequestBody @Valid UpdateOrderStatusDto orderStatusDto) {
        OrderStatus orderStatus = OrderStatus.valueOf(orderStatusDto.getUpdateOrderStatus().toUpperCase());
        return ResponseEntity.ok(restaurantOwnerService.updateOrderStatus(orderId, orderStatus));
    }
}
