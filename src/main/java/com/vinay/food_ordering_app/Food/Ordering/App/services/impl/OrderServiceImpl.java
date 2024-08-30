package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.OrderRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderEntity createNewOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    @Override
    public OrderEntity getOrderDetails(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: "+orderId));
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        OrderEntity order = getOrderDetails(orderId);

        if (orderStatus == null || !EnumSet.allOf(OrderStatus.class).contains(orderStatus)) {
            throw new IllegalArgumentException("Invalid order status provided.");
        }

        order.setOrderStatus(orderStatus);
        OrderEntity updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public OrderDto getAllCustomerOrders(Long customerId) {
        return null;
    }
}
