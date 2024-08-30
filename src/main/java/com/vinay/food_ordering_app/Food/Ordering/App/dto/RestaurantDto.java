package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Long id;

    private String name;

    @JsonIgnore
    private PointDto location;

    private Double rating;

    private RestaurantOwnerDto restaurantOwner;

    private List<MenuItemDto> menuItems;
}
