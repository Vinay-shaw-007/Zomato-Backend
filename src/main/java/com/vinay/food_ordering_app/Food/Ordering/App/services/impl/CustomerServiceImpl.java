package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.*;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.MenuItemEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentMethod;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.CustomerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.CustomerRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.CustomerService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.NotificationService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.UserService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderServiceImpl orderService;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;
    private final RestaurantService restaurantService;


    @Override
    public void createNewCustomer(UserEntity user) {
        CustomerEntity customer = CustomerEntity.builder()
                .user(user)
                .paymentMethod(PaymentMethod.CASH)
                .build();
        customerRepository.save(customer);
    }

    @Override
    public CustomerEntity getCustomerDetails(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: "+customerId));
    }

    @Override
    public CustomerDto updateCustomerProfile(Long customerId) {
        return null;
    }

    @Override
    public CustomerDto requestOrder(Long customerId, Long restaurantId, TakeOrderDto takeOrder) {
        // Retrieve the customer
        CustomerEntity customer = getCustomerDetails(customerId);

        // Retrieve the restaurant details
        RestaurantDto restaurantDto = restaurantService.getRestaurantDetails(restaurantId);

        // Get the menu items from the restaurant
        List<MenuItemDto> restaurantMenuItems = restaurantDto.getMenuItems();

        // Validate and filter the selected menu items
        List<MenuItemDto> validatedItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (MenuItemDto selectedItem : takeOrder.getMenuItems()) {
            Optional<MenuItemDto> matchingItem = restaurantMenuItems.stream()
                    .filter(menuItem -> menuItem.getId().equals(selectedItem.getId()))
                    .findFirst();

            if (matchingItem.isPresent()) {
                validatedItems.add(matchingItem.get());
                totalPrice += matchingItem.get().getPrice();
            } else {
                throw new ResourceNotFoundException("Menu item not found in the restaurant menu.");
            }
        }

        RestaurantEntity restaurant = modelMapper.map(restaurantDto, RestaurantEntity.class);

        Point location = modelMapper.map(takeOrder.getDeliveryLocation(), Point.class);


        // Create the order entity
        OrderEntity order = OrderEntity.builder()
                .deliveryLocation(location)
                .customer(customer)
                .restaurant(restaurant)
                .menuItems(validatedItems.stream()
                        .map(item -> modelMapper.map(item, MenuItemEntity.class))
                        .collect(Collectors.toList()))
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.PLACED)
                .build();

        // Save the order
        OrderEntity savedOrder = orderService.createNewOrder(order);

        //Send notification to restaurant.
        notificationService.sendNotificationToRestaurantId(restaurant, savedOrder);

        // Map the saved order to OrderDto and set it in CustomerDto
        OrderDto orderDto = modelMapper.map(savedOrder, OrderDto.class);

        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);

        customerDto.setOrders(Collections.singletonList(orderDto));

        return customerDto;
    }

}
