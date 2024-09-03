package com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.MenuItemDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.PointDto;
import lombok.Data;

import java.util.List;

@Data
public class TakeOrderDto {

    private PointDto deliveryLocation;

    private List<MenuItemDto> menuItems;

}
