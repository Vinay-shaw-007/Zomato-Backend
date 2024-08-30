package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryPartnerDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;

import java.util.List;

public interface DeliveryPartnerService {

    DeliveryPartnerEntity createDeliveryPartner(DeliveryPartnerEntity deliveryPartner);

    DeliveryPartnerEntity getDeliveryPartnerDetails(Long deliveryPartnerId);

    DeliveryPartnerDto assignDeliveryToDeliveryPartner(Long deliveryPartnerId, DeliveryDto delivery);

    DeliveryPartnerDto updateDeliveryStatus(Long deliveryPartnerId, DeliveryStatus status);

    List<DeliveryPartnerDto> getAllDeliveryPartnerAssignment(Long deliveryPartnerId);
}
