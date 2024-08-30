package com.vinay.food_ordering_app.Food.Ordering.App.controllers;


import com.vinay.food_ordering_app.Food.Ordering.App.dto.DeliveryDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.UpdateDeliveryStatusDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.DeliveryEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "/order/{orderId}")
    public ResponseEntity<DeliveryDto> createDelivery(@PathVariable Long orderId) {
        DeliveryEntity deliveryEntity = deliveryService.createDelivery(orderId);

        return new ResponseEntity<>(modelMapper.map(deliveryEntity, DeliveryDto.class), HttpStatus.CREATED);
    }

    @PostMapping(path = "/{deliveryId}")
    public ResponseEntity<DeliveryDto> updateOrderStatus(@PathVariable Long deliveryId,
                                                      @RequestBody UpdateDeliveryStatusDto deliveryStatus) {
        return ResponseEntity.ok(deliveryService.updateDeliveryStatus(deliveryId, deliveryStatus.getDeliveryStatus()));
    }
}
