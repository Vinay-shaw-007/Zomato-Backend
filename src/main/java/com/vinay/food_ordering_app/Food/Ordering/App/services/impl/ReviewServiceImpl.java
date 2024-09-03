package com.vinay.food_ordering_app.Food.Ordering.App.services.impl;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.ReviewDto;
import com.vinay.food_ordering_app.Food.Ordering.App.dto.utilityDto.RestaurantReviewDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.ReviewEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.CustomerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.ReviewRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestaurantService restaurantService;
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewEntity addReview(CustomerEntity customer, Long restaurantId, RestaurantReviewDto restaurantReviewDto) {
        RestaurantEntity restaurant = restaurantService.getRestaurantDetails(restaurantId);
        ReviewEntity reviewEntity = ReviewEntity
                .builder()
                .customer(customer)
                .restaurant(restaurant)
                .comment(restaurantReviewDto.getComment())
                .rating(restaurantReviewDto.getRating())
                .date(LocalDateTime.now())
                .build();
        return reviewRepository.save(reviewEntity);
    }

    @Override
    public ReviewDto getRestaurantReview(Long restaurantId) {
        return null;
    }
}
