package com.vinay.food_ordering_app.Food.Ordering.App.validation.transition;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OrderStatusTransition {
    private static final Map<OrderStatus, Set<OrderStatus>> validTransitions = new HashMap<>();

    static {
        validTransitions.put(OrderStatus.PLACED, EnumSet.of(OrderStatus.PREPARING));
        validTransitions.put(OrderStatus.PREPARING, EnumSet.of(OrderStatus.READY_FOR_PICKUP));
        validTransitions.put(OrderStatus.READY_FOR_PICKUP, EnumSet.of(OrderStatus.PICKED_UP_BY_DELIVERY_PARTNER));
        validTransitions.put(OrderStatus.PICKED_UP_BY_DELIVERY_PARTNER, EnumSet.of(OrderStatus.DELIVERED));
    }

    public static boolean isValidTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        return validTransitions.containsKey(currentStatus) &&
                validTransitions.get(currentStatus).contains(newStatus);
    }
}

