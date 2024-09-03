package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.UserDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
@Tag(name = "User API", description = "Endpoints for managing user details")
@Secured(value = "ROLE_ADMIN")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Get User Details", description = "Retrieve detailed information about a user.")
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> getUserDetails(
                                                    @Parameter(description = "ID of the user to retrieve details for", required = true)
                                                    @PathVariable Long userId) {
        UserEntity user = userService.getUserDetails(userId);
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }
}
