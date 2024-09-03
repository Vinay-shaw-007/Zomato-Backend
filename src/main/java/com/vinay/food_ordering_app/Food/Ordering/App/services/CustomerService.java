package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.CustomerDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.ReviewDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.WalletDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.RestaurantReviewDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.TakeOrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.WalletEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentMethod;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.CustomerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;

public interface CustomerService {

    void createNewCustomer(UserEntity user);

    CustomerEntity getCustomerDetails(Long customerId);

    CustomerDto requestOrder(Long restaurantId, TakeOrderDto takeOrder);

    CustomerEntity getCurrentCustomer();

    OrderDto cancelOrder(Long orderId);

    OrderDto getOrderDetails(Long orderId);

    CustomerDto updatePaymentMethod(PaymentMethod paymentMethod);

    ReviewDto reviewRestaurant(Long restaurantId, RestaurantReviewDto restaurantReview);

    WalletDto addMoneyToCustomerWallet(Long customerId, Double amount);
}
