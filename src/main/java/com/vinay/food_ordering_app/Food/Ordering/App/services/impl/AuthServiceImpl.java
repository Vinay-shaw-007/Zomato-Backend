package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.*;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.Role;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.RestaurantOwnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.RuntimeConflictException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.UserRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.*;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final WalletService walletService;
    private final DeliveryPartnerService deliveryPartnerService;
    private final RestaurantOwnerService restaurantOwnerService;
    private final RestaurantService restaurantService;

    @Override
    public UserDto signUp(SignUpDto signUpDto) {
        UserEntity user = userRepository.findByEmail(signUpDto.getEmail()).orElse(null);
        if (user != null) {
            throw new RuntimeConflictException("Cannot signup, User already exists with email "+signUpDto.getEmail());
        }

        UserEntity mappedUser = modelMapper.map(signUpDto, UserEntity.class);
        mappedUser.setRoles(Set.of(Role.CUSTOMER));
        UserEntity savedUser = userRepository.save(mappedUser);

        customerService.createNewCustomer(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public DeliveryPartnerDto onBoardDeliveryPartner(Long userId, OnBoardDeliveryPartnerDto onBoardDeliveryPartner) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not fount with id: "+userId));

        if (user.getRoles().contains(Role.DELIVERY_PARTNER))
            throw new RuntimeConflictException("User with id "+userId+" is already a delivery partner.");

        Point partnerLocation = modelMapper.map(onBoardDeliveryPartner.getDeliveryPartnerLocation(), Point.class);

        DeliveryPartnerEntity deliveryPartner = DeliveryPartnerEntity.builder()
                .user(user)
                .vehicleId(onBoardDeliveryPartner.getVehicleId())
                .availabilityStatus(true)
                .currentLocation(partnerLocation)
                .build();
        user.getRoles().add(Role.DELIVERY_PARTNER);
        userRepository.save(user);
        DeliveryPartnerEntity savedDeliveryPartner = deliveryPartnerService.createDeliveryPartner(deliveryPartner);

        return modelMapper.map(savedDeliveryPartner, DeliveryPartnerDto.class);
    }

    @Override
    public RestaurantOwnerDto onBoardRestaurantOwner(Long userId, String registrationNumber) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not fount with id: "+userId));

        if (user.getRoles().contains(Role.RESTAURANT_OWNER))
            throw new RuntimeConflictException("User with id "+userId+" is already have a "+Role.RESTAURANT_OWNER);

        RestaurantOwnerEntity restaurantOwner = RestaurantOwnerEntity.builder()
                .user(user)
                .registrationNumber(registrationNumber)
                .build();
        user.getRoles().add(Role.RESTAURANT_OWNER);
        userRepository.save(user);

        RestaurantOwnerEntity savedOwner = restaurantOwnerService.createRestaurantOwner(restaurantOwner);

        return modelMapper.map(savedOwner, RestaurantOwnerDto.class);
    }

    @Override
    public RestaurantDto onBoardRestaurant(Long ownerId, OnBoardRestaurantDto restaurantDetails) {
        UserEntity user = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not fount with id: "+ownerId));

        if (!user.getRoles().contains(Role.RESTAURANT_OWNER))
            throw new RuntimeConflictException("User does not have a "+Role.RESTAURANT_OWNER+" role.");

        RestaurantOwnerEntity restaurantOwner = restaurantOwnerService.getRestaurantOwnerDetails(user)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not fount with id: "+ownerId));

        Point location = modelMapper.map(restaurantDetails.getLocation(), Point.class);

        RestaurantEntity restaurant = RestaurantEntity.builder()
                .id(null)
                .name(restaurantDetails.getName())
                .rating(0.0)
                .location(location)
                .restaurantOwner(restaurantOwner)
                .build();
        RestaurantEntity savedRestaurant = restaurantService.createNewRestaurant(restaurant);
        return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }
}
