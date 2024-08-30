package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import lombok.Data;

import java.util.List;

@Data
public class TakeOrderDto {

    private PointDto deliveryLocation;

    private List<MenuItemDto> menuItems;

}
