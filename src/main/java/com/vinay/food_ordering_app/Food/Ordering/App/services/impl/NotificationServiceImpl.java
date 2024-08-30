package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.NotificationDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.MenuItemEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender javaMailSender;

    @Override
    public NotificationDto sendNotificationToCustomer(Long customerId) {
        return null;
    }

    @Override
    public void sendNotificationToRestaurantId(RestaurantEntity restaurant, OrderEntity order) {
        String email = restaurant.getRestaurantOwner().getUser().getEmail();
        String notificationMessage = "New Order Received! Order ID: " + order.getId() +
                "\nTotal Price: " + order.getTotalPrice() +
                "\nItems: " + order.getMenuItems().stream()
                .map(MenuItemEntity::getName)
                .collect(Collectors.joining(", "));

        sendPushNotification(email, "Got New Order with ID #" + order.getId(), notificationMessage);
    }

    @Override
    public void notifyDeliveryPartner(DeliveryPartnerEntity deliveryPartner, DeliveryEntity delivery) {
        String message = String.format(
                "You have been assigned a new delivery for Order ID: %d. Please pick it up from %s.",
                delivery.getOrder().getId(),
                delivery.getOrder().getRestaurant().getName()
        );
        String email = deliveryPartner.getUser().getEmail();
        String subject = "New delivery assigned ID #"+delivery.getId();

        sendPushNotification(email, subject, message);
    }



    @Override
    public List<NotificationDto> getAllCustomerNotifications(Long customerId) {
        return List.of();
    }


    private void sendPushNotification(String toEmail, String subject, String body) {
        try {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body); //body

            javaMailSender.send(simpleMailMessage);
            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error("Cannot send email {} ", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
