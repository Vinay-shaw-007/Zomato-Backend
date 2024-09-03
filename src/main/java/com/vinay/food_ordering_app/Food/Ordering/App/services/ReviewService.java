package com.vinay.food_ordering_app.Food.Ordering.App.services;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.ReviewDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.RestaurantReviewDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.ReviewEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.CustomerEntity;

public interface ReviewService {

    ReviewEntity addReview(CustomerEntity customer, Long restaurantId, RestaurantReviewDto restaurantReviewDto);

    ReviewDto getRestaurantReview(Long restaurantId);
}
