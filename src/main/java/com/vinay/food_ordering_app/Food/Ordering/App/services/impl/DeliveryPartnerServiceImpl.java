package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryPartnerDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.UpdateDeliveryPartnerLocation;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.AuthorizationException;
import com.vinay.food_ordering_app.Food.Ordering.App.exceptions.ResourceNotFoundException;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.DeliveryPartnerRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.DeliveryPartnerService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final ModelMapper modelMapper;
    private final DeliveryService deliveryService;

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
    public DeliveryDto updateDeliveryStatus(Long deliveryId, DeliveryStatus status) {
        DeliveryPartnerEntity deliveryPartner = getCurrentDeliveryPartner();

        DeliveryEntity delivery = deliveryService.getDeliveryDetails(deliveryId);

        if (!delivery.getDeliveryPartner().equals(deliveryPartner)) {
            throw new AuthorizationException("You are not the authorized to update the status of this delivery.");
        }

        return deliveryService.updateDeliveryStatus(delivery, status);
    }

    @Override
    public DeliveryPartnerEntity updateDeliveryPartnerLocation(UpdateDeliveryPartnerLocation location) {

        DeliveryPartnerEntity deliveryPartner = getCurrentDeliveryPartner();

        deliveryPartner.setCurrentLocation(modelMapper.map(location.getDeliveryPartnerLocation(), Point.class));

        return modelMapper.map(deliveryPartnerRepository.save(deliveryPartner), DeliveryPartnerEntity.class);
    }

    @Override
    public DeliveryPartnerEntity getCurrentDeliveryPartner() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return deliveryPartnerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DeliveryPartner not associated with user with ID: "+user.getId()
                ));
    }
}
