package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.*;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.RestaurantReviewDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.TakeOrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.*;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentMethod;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.CustomerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.AuthorizationException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.RuntimeConflictException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.CustomerRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.*;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final DeliveryService deliveryService;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;
    private final RestaurantService restaurantService;
    private final WalletService walletService;
    private final ReviewService reviewService;


    @Override
    public void createNewCustomer(UserEntity user) {
        CustomerEntity customer = CustomerEntity.builder()
                .user(user)
                .paymentMethod(PaymentMethod.CASH_ON_DELIVERY)
                .build();
        customerRepository.save(customer);
    }

    @Override
    public CustomerEntity getCustomerDetails(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: "+customerId));
    }


    @Override
    @Transactional
    public CustomerDto requestOrder(Long restaurantId, TakeOrderDto takeOrder) {
        // Retrieve the customer
        CustomerEntity customer = getCurrentCustomer();

        // Retrieve the restaurant details
        RestaurantDto restaurantDto = restaurantService.getRestaurantDetailsWithMenuItems(restaurantId);

        // Get the menu items from the restaurant
        List<MenuItemDto> restaurantMenuItems = restaurantDto.getMenuItems();

        // Validate and filter the selected menu items
        List<MenuItemDto> validatedItems = new ArrayList<>();

        double totalPrice = validateMenuItemsAndGetTotalPrice(validatedItems, takeOrder, restaurantMenuItems);

        // Create Payment Entity
        PaymentEntity payment = PaymentEntity.builder()
                .paymentMethod(customer.getPaymentMethod())
                .amount(totalPrice)
                .build();


        if (customer.getPaymentMethod().equals(PaymentMethod.CASH_ON_DELIVERY)) {
            payment.setPaymentStatus(PaymentStatus.PENDING);
        } else {
            WalletEntity wallet = walletService.getUserWallet(customer.getUser());
            if (wallet.getBalance() - totalPrice >= 0) {
                walletService.debitMoneyFromWallet(wallet, totalPrice);
                payment.setPaymentStatus(PaymentStatus.COMPLETED);
                payment.setWallet(wallet);
                payment.setPaymentTime(LocalDateTime.now());
            } else {
                throw new ResourceNotFoundException("Your wallet do not have enough balance.");
            }
        }

        RestaurantEntity restaurant = modelMapper.map(restaurantDto, RestaurantEntity.class);

        Point deliveryLocation = modelMapper.map(takeOrder.getDeliveryLocation(), Point.class);


        // Create the order entity
        OrderEntity order = OrderEntity.builder()
                .deliveryLocation(deliveryLocation)
                .customer(customer)
                .restaurant(restaurant)
                .menuItems(validatedItems.stream()
                        .map(item -> modelMapper.map(item, MenuItemEntity.class))
                        .collect(Collectors.toList()))
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.PLACED)
                .build();



        order.setPayment(payment);

        payment.setOrder(order);

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

    @Override
    @Transactional
    public OrderDto cancelOrder(Long orderId) {
        CustomerEntity customer = getCurrentCustomer();

        OrderEntity order = orderService.getOrderDetails(orderId);

        if (!order.getCustomer().equals(customer)) {
            throw new AuthorizationException("You are not authorized to cancel this order.");
        }

        if (order.getOrderStatus().equals(OrderStatus.DELIVERED)) {
            throw new RuntimeConflictException("Order is already delivered.");
        }


        // This is used to cancel the delivery because when restaurant change the order status to ready for pickup, automatically a delivery is created and a delivery partner is associated with it.
        if (order.getOrderStatus().equals(OrderStatus.READY_FOR_PICKUP) || order.getOrderStatus().equals(OrderStatus.PICKED_UP_BY_DELIVERY_PARTNER)) {
            DeliveryEntity delivery = deliveryService.findByOrder_Id(orderId);
            deliveryService.cancelDelivery(delivery);
        }

        if (customer.getPaymentMethod().equals(PaymentMethod.WALLET))
            handleRefundMoney(customer.getUser().getWallet(), order);
        else
            order.getPayment().setPaymentStatus(PaymentStatus.CANCELLED);

        return orderService.cancelOrder(order);
    }

    private void handleRefundMoney(WalletEntity wallet, OrderEntity order) {
        walletService.addMoneyToWallet(wallet, order.getTotalPrice());
        order.getPayment().setPaymentStatus(PaymentStatus.REFUND);
    }

    @Override
    public OrderDto getOrderDetails(Long orderId) {
        CustomerEntity customer = getCurrentCustomer();
        OrderEntity order = orderService.getOrderDetails(orderId);

        if (!order.getCustomer().equals(customer)) {
            throw new AuthorizationException("You did not initiated this order, hence not allowed to see the details.");
        }

        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public CustomerDto updatePaymentMethod(PaymentMethod paymentMethod) {
        CustomerEntity customer = getCurrentCustomer();

        customer.setPaymentMethod(paymentMethod);

        return modelMapper.map(customerRepository.save(customer), CustomerDto.class);
    }

    @Override
    public ReviewDto reviewRestaurant(Long restaurantId, RestaurantReviewDto restaurantReview) {
        CustomerEntity customer = getCurrentCustomer();
        ReviewEntity reviewEntity = reviewService.addReview(customer, restaurantId, restaurantReview);
        return modelMapper.map(reviewEntity, ReviewDto.class);
    }

    @Override
    public WalletDto addMoneyToCustomerWallet(Long customerId, Double amount) {
        CustomerEntity customer = getCustomerDetails(customerId);

        WalletEntity wallet = customer.getUser().getWallet();

        return modelMapper.map(walletService.addMoneyToWallet(wallet, amount), WalletDto.class);
    }


    @Override
    public CustomerEntity getCurrentCustomer() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not associated with user with ID: "+user.getId()
                ));
    }

//  <--------------------------------------- INTERNAL FUNCTION ------------------------------------------>

    private double validateMenuItemsAndGetTotalPrice(List<MenuItemDto> validatedItems, TakeOrderDto takeOrder, List<MenuItemDto> restaurantMenuItems) {
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

        return totalPrice;
    }


}
