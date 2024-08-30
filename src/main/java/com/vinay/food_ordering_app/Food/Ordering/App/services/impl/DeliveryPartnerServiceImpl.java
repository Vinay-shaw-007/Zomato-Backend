package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryPartnerDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.DeliveryPartnerRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.DeliveryPartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    @Override
    public DeliveryPartnerEntity createDeliveryPartner(DeliveryPartnerEntity deliveryPartner) {
        return deliveryPartnerRepository.save(deliveryPartner);
    }

    @Override
    public DeliveryPartnerEntity getDeliveryPartnerDetails(Long deliveryPartnerId) {
        return deliveryPartnerRepository.findById(deliveryPartnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery Partner not found with id: "+deliveryPartnerId));
    }

    @Override
    public DeliveryPartnerDto assignDeliveryToDeliveryPartner(Long deliveryPartnerId, DeliveryDto delivery) {
        return null;
    }

    @Override
    public DeliveryPartnerDto updateDeliveryStatus(Long deliveryPartnerId, DeliveryStatus status) {
        return null;
    }

    @Override
    public List<DeliveryPartnerDto> getAllDeliveryPartnerAssignment(Long deliveryPartnerId) {
        return List.of();
    }
}
