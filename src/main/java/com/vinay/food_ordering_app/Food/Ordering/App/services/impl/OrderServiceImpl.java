package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.RuntimeConflictException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.OrderRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.DeliveryService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.OrderService;
import com.vinay.food_ordering_app.Food.Ordering.App.validation.transition.OrderStatusTransition;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
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
    public OrderDto updateOrderStatus(OrderEntity order, OrderStatus orderStatus) {

        if (!OrderStatusTransition.isValidTransition(order.getOrderStatus(), orderStatus)) {
            throw new IllegalStateException("Cannot transition from " + order.getOrderStatus() + " to " + orderStatus);
        }

        order.setOrderStatus(orderStatus);

        if (orderStatus.equals(OrderStatus.READY_FOR_PICKUP)) {
            deliveryService.createDelivery(order);
        }

        OrderEntity updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public OrderDto cancelOrder(OrderEntity order) {

        order.setOrderStatus(OrderStatus.CANCELLED);

        OrderEntity updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }
}
