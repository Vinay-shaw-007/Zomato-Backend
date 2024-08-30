package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;

public interface OrderService {

    OrderEntity createNewOrder(OrderEntity order);

    OrderEntity getOrderDetails(Long orderId);

    OrderDto updateOrderStatus(Long orderId, OrderStatus orderStatus);

    OrderDto getAllCustomerOrders(Long customerId);
}
