package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.UpdateDeliveryPartnerLocation;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.UpdateDeliveryStatusDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.DeliveryStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.services.DeliveryPartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Secured(value = "ROLE_DELIVERY_PARTNER")
@Tag(name = "Delivery Partner API", description = "Endpoints for delivery partners to update status and location")
@RequestMapping(path = "/delivery-partner")
public class DeliveryPartnerController {

    private final DeliveryPartnerService deliveryPartnerService;

    @Operation(summary = "Update Order Status", description = "Update the status of a delivery by the delivery partner")
    @PostMapping(path = "/delivery/{deliveryId}")
    public ResponseEntity<DeliveryDto> updateOrderStatus(
                                                        @Parameter(description = "ID of the delivery to update", required = true)
                                                        @PathVariable Long deliveryId,
                                                        @Parameter(description = "New status for the delivery", required = true)
                                                        @RequestBody @Valid UpdateDeliveryStatusDto deliveryStatusDto) {
        DeliveryStatus deliveryStatus = DeliveryStatus.valueOf(deliveryStatusDto.getDeliveryStatus().toUpperCase());

        return ResponseEntity.ok(deliveryPartnerService.updateDeliveryStatus(deliveryId, deliveryStatus));
    }

    @Operation(summary = "Update Delivery Partner Location", description = "Update the current location of the delivery partner")
    @PostMapping(path = "/update-delivery-partner-location")
    public ResponseEntity<DeliveryPartnerEntity> updateDeliveryPartnerLocation(
                                                                                @Parameter(description = "Location details of the delivery partner", required = true)
                                                                                @RequestBody UpdateDeliveryPartnerLocation location) {

        return ResponseEntity.ok(deliveryPartnerService.updateDeliveryPartnerLocation(location));
    }
}
