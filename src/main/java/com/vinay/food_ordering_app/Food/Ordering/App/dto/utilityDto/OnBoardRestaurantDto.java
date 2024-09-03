package com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.PointDto;
import lombok.Data;

@Data
public class OnBoardRestaurantDto {

    private String name;

    private PointDto location;
}
