package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.*;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.*;
import com.vinay.food_ordering_app.Food.Ordering.App.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Endpoints for user authentication and onboarding")
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Sign up", description = "Create a new user account")
    @PostMapping(path = "/signup")
    ResponseEntity<UserDto> signUp(
                                    @Parameter(description = "Sign-up details of the new user", required = true)
                                    @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

    @Operation(summary = "Login", description = "Authenticate user and return access and refresh tokens")
    @PostMapping(path = "/login")
    ResponseEntity<LoginResponseDto> login(
                                            @Parameter(description = "Login request containing user's email and password", required = true)
                                            @RequestBody LoginRequestDto loginRequestDto,
                                            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String[] tokens = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        Cookie cookie = new Cookie("refreshToken", tokens[1]);
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }

    @Operation(summary = "Refresh token", description = "Refresh the access token using the refresh token stored in cookies")
    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        String accessToke = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToke));
    }

    @Secured(value = "ROLE_ADMIN")
    @Operation(summary = "Onboard Delivery Partner", description = "Onboard a new delivery partner to the system")
    @PostMapping(path = "/on-board-delivery-partner/user/{userId}")
    ResponseEntity<DeliveryPartnerDto> onBoardDeliveryPartner(
                                                                @Parameter(description = "ID of the user to be onboarded as a delivery partner", required = true)
                                                                @PathVariable Long userId,
                                                                @RequestBody OnBoardDeliveryPartnerDto deliveryPartner) {
        return new ResponseEntity<>(authService.onBoardDeliveryPartner(userId, deliveryPartner), HttpStatus.CREATED);
    }

    @Secured(value = "ROLE_ADMIN")
    @Operation(summary = "Onboard Restaurant Owner", description = "Onboard a new restaurant owner to the system")
    @PostMapping(path = "/on-board-restaurant-owner/user/{userId}")
    ResponseEntity<RestaurantOwnerDto> onBoardRestaurantOwner(
                                                                @Parameter(description = "ID of the user to be onboarded as a restaurant owner", required = true)
                                                                @PathVariable Long userId,
                                                                @RequestBody OnBoardRestaurantOwnerDto restaurantOwner) {
        return new ResponseEntity<>(authService.onBoardRestaurantOwner(userId, restaurantOwner.getRegistrationNumber()), HttpStatus.CREATED);
    }

    @Secured(value = "ROLE_ADMIN")
    @Operation(summary = "Onboard Restaurant", description = "Onboard a new restaurant under an existing restaurant owner")
    @PostMapping(path = "/on-board-restaurant/restaurant-owner/{userId}")
    ResponseEntity<RestaurantDto> onBoardRestaurant(
                                                    @Parameter(description = "ID of the restaurant owner to whom the restaurant belongs", required = true)
                                                    @PathVariable Long userId,
                                                    @RequestBody OnBoardRestaurantDto restaurantDetails) {
        return new ResponseEntity<>(authService.onBoardRestaurant(userId, restaurantDetails), HttpStatus.CREATED);
    }
}
