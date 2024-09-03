package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.PaymentEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.WalletEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentMethod;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.NoAvailableDeliveryPartnerException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.OrderNotReadyException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.DeliveryRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.*;
import com.vinay.food_ordering_app.Food.Ordering.App.strategies.DeliveryPartnerFindingStrategy;
import com.vinay.food_ordering_app.Food.Ordering.App.validation.transition.DeliveryStatusTransition;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

//    private final OrderService orderService;
    private final DeliveryPartnerFindingStrategy partnerFindingStrategy;
    private final DeliveryRepository deliveryRepository;
    private final NotificationService notificationService;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;
    private final WalletService walletService;

    @Override
    public DeliveryEntity createDelivery(OrderEntity order) {

        // Find delivery partner who is nearest to the restaurant
        DeliveryPartnerEntity deliveryPartner = partnerFindingStrategy.findNearestDeliveryPartner(order.getRestaurant());

        // Check if a delivery partner was found
        if (deliveryPartner == null) {
            throw new NoAvailableDeliveryPartnerException("No delivery partner available at this moment.");
        }

        // Create the delivery entity
        DeliveryEntity deliveryEntity = DeliveryEntity.builder()
                .order(order)
                .amount(order.getTotalPrice())
                .deliveryLocation(order.getDeliveryLocation())
                .deliveryStatus(DeliveryStatus.ASSIGNED)
                .deliveryStarted(LocalDateTime.now())
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
    public DeliveryEntity findByOrder_Id(Long orderId) {
        return deliveryRepository.findByOrder_Id(orderId);
    }


    @Override
    @Transactional
    public DeliveryDto updateDeliveryStatus(DeliveryEntity delivery, DeliveryStatus deliveryStatus) {

        validateDeliveryTransition(delivery, deliveryStatus);

        if (deliveryStatus.equals(DeliveryStatus.COMPLETED)) {
            handlePaymentCompletion(delivery);
            delivery.setDeliveryCompleted(LocalDateTime.now());

            OrderEntity order = delivery.getOrder();
            order.setOrderStatus(OrderStatus.DELIVERED);
            delivery.setOrder(order);
        } else if (deliveryStatus.equals(DeliveryStatus.OUT_OF_DELIVERY)) { // update order status to picked up by delivery partner once delivery partner change the status to out of delivery.
            OrderEntity order = delivery.getOrder();
            order.setOrderStatus(OrderStatus.PICKED_UP_BY_DELIVERY_PARTNER);
            delivery.setOrder(order);
        }


        delivery.setDeliveryStatus(deliveryStatus);
        DeliveryEntity updatedDelivery = deliveryRepository.save(delivery);

        return modelMapper.map(updatedDelivery, DeliveryDto.class);
    }

    @Override
    public DeliveryEntity cancelDelivery(DeliveryEntity delivery) {
        delivery.setDeliveryStatus(DeliveryStatus.CANCELLED);
        return deliveryRepository.save(delivery);
    }

//  <----------------------------------------- INTERNAL FUNCTIONS ------------------------------------------------->

    private void validateDeliveryTransition(DeliveryEntity delivery, DeliveryStatus newStatus) {
        if (!DeliveryStatusTransition.isValidTransition(delivery.getDeliveryStatus(), newStatus)) {
            throw new IllegalStateException("Cannot transition from " + delivery.getDeliveryStatus() + " to " + newStatus);
        }
    }

    private void handlePaymentCompletion(DeliveryEntity delivery) {
        PaymentEntity payment = delivery.getOrder().getPayment();
        if (payment.getPaymentMethod().equals(PaymentMethod.CASH_ON_DELIVERY)) {
            payment.setPaymentTime(LocalDateTime.now());
            paymentService.updatePaymentStatus(payment, PaymentStatus.COMPLETED);
        } else {
            // add the amount to the restaurant Owner wallet.
            WalletEntity restaurantOwnerWallet = delivery.getOrder().getRestaurant().getRestaurantOwner().getUser().getWallet();
            walletService.addMoneyToWallet(restaurantOwnerWallet, delivery.getAmount());
        }
    }
}
