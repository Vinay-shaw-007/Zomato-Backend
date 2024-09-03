package com.vinay.food_ordering_app.Food.Ordering.App.repositories;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
