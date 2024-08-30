package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.UpdateOrderStatusDto;
import com.vinay.food_ordering_app.Food.Ordering.App.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/{orderId}")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId,
                                                      @RequestBody UpdateOrderStatusDto orderStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, orderStatus.getUpdateOrderStatus()));
    }

}
