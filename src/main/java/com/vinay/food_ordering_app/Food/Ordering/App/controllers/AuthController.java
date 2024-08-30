package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.*;
import com.vinay.food_ordering_app.Food.Ordering.App.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

    @PostMapping(path = "/onBoardDeliveryPartner/{userId}")
    ResponseEntity<DeliveryPartnerDto> onBoardDeliveryPartner(@PathVariable Long userId,
                                                              @RequestBody OnBoardDeliveryPartnerDto deliveryPartner) {
        return new ResponseEntity<>(authService.onBoardDeliveryPartner(userId, deliveryPartner), HttpStatus.CREATED);
    }

    @PostMapping(path = "/onBoardRestaurantOwner/{userId}")
    ResponseEntity<RestaurantOwnerDto> onBoardRestaurantOwner(@PathVariable Long userId,
                                                         @RequestBody OnBoardRestaurantOwnerDto restaurantOwner) {
        return new ResponseEntity<>(authService.onBoardRestaurantOwner(userId, restaurantOwner.getRegistrationNumber()), HttpStatus.CREATED);
    }

    @PostMapping(path = "/onBoardRestaurant/{userId}")
    ResponseEntity<RestaurantDto> onBoardRestaurant(@PathVariable Long userId,
                                                    @RequestBody OnBoardRestaurantDto restaurantDetails) {
        return new ResponseEntity<>(authService.onBoardRestaurant(userId, restaurantDetails), HttpStatus.CREATED);
    }
}
