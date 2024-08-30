package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.ReviewDto;

public interface ReviewService {

    ReviewDto addReview(Long restaurantId, ReviewDto reviewDto);

    ReviewDto getRestaurantReview(Long restaurantId);
}
