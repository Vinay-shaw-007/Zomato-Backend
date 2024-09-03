package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {

    private Long id;

    private CustomerDto customer;

    private RestaurantDto restaurant;

    private Double rating;

    private String comment;

    private LocalDateTime date;
}
