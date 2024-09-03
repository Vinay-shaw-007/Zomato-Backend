package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.MenuItemEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.OrderStatus;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    @JsonIgnore
    private CustomerDto customer;

    @JsonIgnore
    private RestaurantDto restaurant;

    private List<MenuItemDto> menuItems;

    private Double totalPrice;

    private OrderStatus orderStatus;

    private PointDto deliveryLocation;

    private PaymentDto payment;

}
