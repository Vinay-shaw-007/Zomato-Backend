package com.vinay.food_ordering_app.Food.Ordering.App.repositories;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.WalletEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

     Optional<WalletEntity> findByUser(UserEntity user);
}
