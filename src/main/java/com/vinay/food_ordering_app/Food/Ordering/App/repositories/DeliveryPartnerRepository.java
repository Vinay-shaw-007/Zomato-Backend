package com.vinay.food_ordering_app.Food.Ordering.App.repositories;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartnerEntity, Long> {

    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :restaurantLocation) AS distance " +
            "FROM delivery_partner d " +
            "WHERE d.availability_status = true AND ST_DWithin(d.current_location, :restaurantLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 1", nativeQuery = true)
    DeliveryPartnerEntity findNearestDeliveryPartner(Point restaurantLocation);

    Optional<DeliveryPartnerEntity> findByUser(UserEntity user);
}

