package com.vinay.food_ordering_app.Food.Ordering.App.validation.transition;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeliveryStatusTransition {
    private static final Map<DeliveryStatus, Set<DeliveryStatus>> validTransitions = new HashMap<>();

    static {
        validTransitions.put(DeliveryStatus.ASSIGNED, EnumSet.of(DeliveryStatus.OUT_OF_DELIVERY));
        validTransitions.put(DeliveryStatus.OUT_OF_DELIVERY, EnumSet.of(DeliveryStatus.COMPLETED));
    }

    public static boolean isValidTransition(DeliveryStatus currentStatus, DeliveryStatus newStatus) {
        return validTransitions.containsKey(currentStatus) &&
                validTransitions.get(currentStatus).contains(newStatus);
    }
}
