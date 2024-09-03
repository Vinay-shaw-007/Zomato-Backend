package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.PaymentEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

    private Long id;

    @JsonIgnore
    private UserDto user;

    private Double balance = 0.0;

    private List<PaymentDto> payments;
}
