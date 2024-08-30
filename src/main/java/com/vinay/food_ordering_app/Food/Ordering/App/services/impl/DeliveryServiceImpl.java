package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.NoAvailableDeliveryPartnerException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.OrderNotReadyException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.DeliveryRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.DeliveryService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.NotificationService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.OrderService;
import com.vinay.food_ordering_app.Food.Ordering.App.strategies.DeliveryPartnerFindingStrategy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderService orderService;
    private final DeliveryPartnerFindingStrategy partnerFindingStrategy;
    private final DeliveryRepository deliveryRepository;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;

    @Override
    public DeliveryEntity createDelivery(Long orderId) {

        OrderEntity order = orderService.getOrderDetails(orderId);

        DeliveryPartnerEntity deliveryPartner = partnerFindingStrategy.findNearestDeliveryPartner(order.getRestaurant());

        // Check if a delivery partner was found
        if (deliveryPartner == null) {
            throw new NoAvailableDeliveryPartnerException("No delivery partner available at this moment.");
        }

        // Check if the order status is READY_FOR_PICKUP
        if (order.getOrderStatus() != OrderStatus.READY_FOR_PICKUP) {
            throw new OrderNotReadyException("Order is not ready for pickup yet.");
        }

        // Create the delivery entity
        DeliveryEntity deliveryEntity = DeliveryEntity.builder()
                .order(order)
                .deliveryStatus(DeliveryStatus.ASSIGNED)
                .deliveryTime(LocalDateTime.now())
                .deliveryPartner(deliveryPartner)
                .build();

        // Save the delivery entity to the database
        DeliveryEntity savedDeliveryEntity = deliveryRepository.save(deliveryEntity);

        // Send notification to the Delivery Partner
        notificationService.notifyDeliveryPartner(deliveryPartner, savedDeliveryEntity);

        return savedDeliveryEntity;
    }

    @Override
    public DeliveryEntity getDeliveryDetails(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: "+deliveryId));
    }

    @Override
    public DeliveryDto updateDeliveryStatus(Long orderId, DeliveryStatus deliveryStatus) {
        DeliveryEntity delivery = getDeliveryDetails(orderId);

        if (deliveryStatus == null || !EnumSet.allOf(DeliveryStatus.class).contains(deliveryStatus)) {
            throw new IllegalArgumentException("Invalid delivery status provided.");
        }

        delivery.setDeliveryStatus(deliveryStatus);
        DeliveryEntity updatedDelivery = deliveryRepository.save(delivery);

        return modelMapper.map(updatedDelivery, DeliveryDto.class);
    }
}
