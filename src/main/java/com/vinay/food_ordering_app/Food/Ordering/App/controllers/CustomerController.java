package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.CustomerDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.TakeOrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(path = "/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<CustomerDto> requestOrder(@PathVariable Long customerId,
                                                    @PathVariable Long restaurantId,
                                                    @RequestBody TakeOrderDto takeOrder) {
        return new ResponseEntity<>(customerService.requestOrder(customerId, restaurantId, takeOrder), HttpStatus.CREATED);
    }

}
