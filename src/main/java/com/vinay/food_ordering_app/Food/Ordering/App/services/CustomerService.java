package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.CustomerDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.TakeOrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.CustomerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;

import java.util.List;

public interface CustomerService {

    void createNewCustomer(UserEntity user);

    CustomerEntity getCustomerDetails(Long customerId);

    CustomerDto updateCustomerProfile(Long customerId);

    CustomerDto requestOrder(Long customerId, Long restaurantId, TakeOrderDto takeOrder);
}
