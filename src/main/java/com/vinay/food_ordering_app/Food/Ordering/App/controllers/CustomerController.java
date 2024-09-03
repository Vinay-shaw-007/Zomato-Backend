package com.vinay.food_ordering_app.Food.Ordering.App.controllers;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.CustomerDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.OrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.ReviewDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.WalletDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.AddMoneyToWalletDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.RestaurantReviewDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.TakeOrderDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.UpdatePaymentMethod;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.PaymentMethod;
import com.vinay.food_ordering_app.Food.Ordering.App.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@Secured(value = "ROLE_CUSTOMER")
@Tag(name = "Customer API", description = "Endpoints for managing customer orders and payment methods")
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Request an order", description = "Create an order from a specific restaurant by its ID")
    @PostMapping(path = "/restaurant/{restaurantId}")
    public ResponseEntity<CustomerDto> requestOrder(
                                                    @Parameter(description = "ID of the restaurant where the order is being placed", required = true)
                                                    @PathVariable Long restaurantId,
                                                    @RequestBody TakeOrderDto takeOrder) {
        return new ResponseEntity<>(customerService.requestOrder(restaurantId, takeOrder), HttpStatus.CREATED);
    }

    @Operation(summary = "Cancel an order", description = "Cancel an existing order by its ID")
    @PostMapping(path = "/order/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(
                                                @Parameter(description = "ID of the order to be cancelled", required = true)
                                                @PathVariable Long orderId) {
        return ResponseEntity.ok(customerService.cancelOrder(orderId));
    }

    @Operation(summary = "Get order details", description = "Retrieve details of an existing order by its ID")
    @GetMapping(path = "/order/{orderId}")
    public ResponseEntity<OrderDto> getOrderDetails(
                                                    @Parameter(description = "ID of the order to retrieve details", required = true)
                                                    @PathVariable Long orderId) {
        return ResponseEntity.ok(customerService.getOrderDetails(orderId));
    }

    @Operation(summary = "Update payment method", description = "Update the payment method for the customer")
    @PutMapping(value = "/update-payment-method")
    public ResponseEntity<CustomerDto> updatePaymentMethod(
                                                            @Parameter(description = "DTO containing the new payment method", required = true)
                                                            @RequestBody @Valid UpdatePaymentMethod paymentMethodDto) {
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodDto.getPaymentMethod().toUpperCase());

        return ResponseEntity.ok(customerService.updatePaymentMethod(paymentMethod));
    }

    @Operation(summary = "Review Restaurant", description = "Review a restaurant about its food items")
    @PostMapping(path = "/review/restaurant/{restaurantId}")
    public ResponseEntity<ReviewDto> reviewRestaurant(
                                                    @Parameter(description = "ID of the restaurant to be reviewed", required = true)
                                                    @PathVariable Long restaurantId,
                                                    @RequestBody RestaurantReviewDto restaurantReview) {
        return ResponseEntity.ok(customerService.reviewRestaurant(restaurantId, restaurantReview));
    }

    @Secured(value = "ROLE_ADMIN")
    @Operation(summary = "Add Money To Wallet", description = "Add Money to customer wallet")
    @PutMapping(path = "{customerId}/addMoney")
    public ResponseEntity<WalletDto> addMoneyToCustomerWallet(
            @Parameter(description = "ID of the restaurant to be reviewed", required = true)
            @PathVariable Long customerId,
            @RequestBody AddMoneyToWalletDto addMoneyToWalletDto) {
        return ResponseEntity.ok(customerService.addMoneyToCustomerWallet(customerId, addMoneyToWalletDto.getAmount()));
    }
}
