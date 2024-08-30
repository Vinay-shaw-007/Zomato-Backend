package com.vinay.food_ordering_app.Food.Ordering.App.dto;

import com.vinay.food_ordering_app.Food.Ordering.App.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private Set<Role> roles;
}
